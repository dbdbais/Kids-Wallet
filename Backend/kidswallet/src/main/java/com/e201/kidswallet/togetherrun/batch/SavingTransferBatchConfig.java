package com.e201.kidswallet.togetherrun.batch;

import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.SavingTransferProcessorDto;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractPaymentCheck;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractStatus;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.togetherrun.service.TogetherRunService;
import com.e201.kidswallet.user.entity.Relation;
import lombok.NonNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class SavingTransferBatchConfig {

    private int currentPage = 0;
    private final int chunkSize = 10;
    @Bean
    public Job savingTransferJob(JobRepository jobRepository, Step savingTransferStep) {
        return new JobBuilder("savingTransferJob", jobRepository)
                .start(savingTransferStep)
                .build();
    }

    @Bean
    public Step savingTransferStep(@Qualifier("kidswalletJobRepository") JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   ItemReader<SavingContract> depositedContractReader,
                                   ItemProcessor<SavingContract, SavingTransferProcessorDto> paymentProcessor,
                                   ItemWriter<SavingTransferProcessorDto> paymentResultWriter) {
        return new StepBuilder("step", jobRepository).<SavingContract, SavingTransferProcessorDto>chunk(10, transactionManager)
                .reader(depositedContractReader)
                .processor(paymentProcessor)
                .writer(paymentResultWriter)
                .build();
    }

    @Bean
    public ItemReader<SavingContract> depositedContractReader(SavingContractRepository savingContractRepository) {
        return new ItemReader<SavingContract>() {
            private List<SavingContract> currentChunk = null;

            @Override
            public SavingContract read() {
                if (currentChunk == null || currentChunk.isEmpty()) {
                    Pageable pageable = PageRequest.of(currentPage, chunkSize);
                    currentChunk = savingContractRepository.findDepositContractForToday(pageable);
                    currentPage++;
                }
                if (!currentChunk.isEmpty()) {
                    return currentChunk.remove(0);
                } else {
                    return null;
                }
            }
        };
    }

    @Bean
    @Transactional
    public ItemProcessor<SavingContract, SavingTransferProcessorDto> paymentProcessor(TogetherRunRepository togetherRunRepository, AccountService accountService, TogetherRunService togetherRunService) {
        return new ItemProcessor<SavingContract, SavingTransferProcessorDto>() {
            @Override
            public SavingTransferProcessorDto process(@NonNull SavingContract contract) {

                TogetherRun togetherRun = togetherRunRepository.findBySavingContractId(contract.getSavingContractId()).orElse(null);
                if (togetherRun == null) {
                    return null;
                }

                TransferMoneyDTO childTransferMoneyDTO = null;
                TransferMoneyDTO parentsTransferMoneyDTO = null;
                if (contract.getPaymentCheck() == SavingContractPaymentCheck.PAYMENT)
                {
                    childTransferMoneyDTO = TransferMoneyDTO.builder()
                            .fromId(togetherRun.getChildAccount())
                            .toId(contract.getSavingAccount())
                            .amount(togetherRun.getChildContribute().intValue())
                            .build();

                    parentsTransferMoneyDTO = TransferMoneyDTO.builder()
                            .fromId(togetherRun.getParentsAccount())
                            .toId(contract.getSavingAccount())
                            .amount(togetherRun.getParentsContribute().intValue())
                            .build();
                } else {
                    childTransferMoneyDTO = TransferMoneyDTO.builder()
                            .fromId(contract.getSavingAccount())
                            .toId(togetherRun.getChildAccount())
                            .amount(togetherRun.getChildContribute().intValue() * 2)
                            .build();
                    parentsTransferMoneyDTO = TransferMoneyDTO.builder()
                            .fromId(contract.getSavingAccount())
                            .toId(togetherRun.getParentsAccount())
                            .amount(togetherRun.getParentsContribute().intValue() * 2)
                            .build();
                }

                StatusCode childTransferResult = null;
                StatusCode parentsTransferResult = null;
                childTransferResult = accountService.transferMoney(childTransferMoneyDTO);
                parentsTransferResult = accountService.transferMoney(parentsTransferMoneyDTO);
                if (childTransferResult.getHttpStatus() == StatusCode.BAD_REQUEST.getHttpStatus() || parentsTransferResult.getHttpStatus() == StatusCode.BAD_REQUEST.getHttpStatus()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    if (contract.getPaymentCheck() == SavingContractPaymentCheck.NONPAYMENT) {
                        togetherRunService.togetherRunDelete(contract.getSavingContractId());
                    }
                    return SavingTransferProcessorDto.builder()
                            .paymentSuccess(false)
                            .paymentCheck(contract.getPaymentCheck())
                            .build();
                }

                Relation relation = togetherRun.getRelation();
                SavingPayment childPayment = SavingPayment.builder()
                        .user(relation.getChild())
                        .depositAmount(togetherRun.getChildContribute())
                        .depositDate(LocalDateTime.now())
                        .savingContract(contract)
                        .build();
                SavingPayment parentsPayment = SavingPayment.builder()
                        .user(relation.getParent())
                        .depositAmount(togetherRun.getParentsContribute())
                        .depositDate(LocalDateTime.now())
                        .savingContract(contract)
                        .build();

                return SavingTransferProcessorDto.builder()
                        .savingContractId(contract.getSavingContractId())
                        .childPayment(childPayment)
                        .parentsPayment(parentsPayment)
                        .paymentSuccess(true)
                        .build();
            }
        };
    }

    @Bean
    public ItemWriter<SavingTransferProcessorDto> paymentResultWriter(SavingContractRepository savingContractRepository, SavingPaymentRepository savingPaymentRepository) {
        return new ItemWriter<SavingTransferProcessorDto>() {
            @Override
            public void write(Chunk<? extends SavingTransferProcessorDto> chunk) throws Exception {
                List<? extends SavingTransferProcessorDto> items = chunk.getItems();
                SavingContract contract = null;
                for (SavingTransferProcessorDto item : items) {
                    try {
                        contract = savingContractRepository.findById(item.getSavingContractId()).orElse(null);
                        if (!item.isPaymentSuccess()) {
                            contract.setPaymentCheck(SavingContractPaymentCheck.NONPAYMENT);
                        } else {
                            savingPaymentRepository.save(item.getChildPayment());
                            savingPaymentRepository.save(item.getParentsPayment());
                            contract.setCurrentAmount(contract.getCurrentAmount().add(item.getChildPayment().getDepositAmount()).add(item.getParentsPayment().getDepositAmount()));
                        }
                        savingContractRepository.save(contract);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}