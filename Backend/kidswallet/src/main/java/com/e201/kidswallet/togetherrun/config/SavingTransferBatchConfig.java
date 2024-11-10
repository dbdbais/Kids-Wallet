package com.e201.kidswallet.togetherrun.config;

import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.togetherrun.dto.SavingTransferProcessorDto;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.SavingContractStatus;
import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.user.entity.Relation;
import lombok.NonNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableBatchProcessing
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
    public Step savingTransferStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
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
                System.out.println("Reader read");

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
    public ItemProcessor<SavingContract, SavingTransferProcessorDto> paymentProcessor(TogetherRunRepository togetherRunRepository, AccountService accountService) {
        return new ItemProcessor<SavingContract, SavingTransferProcessorDto>() {
            @Override
            public SavingTransferProcessorDto process(@NonNull SavingContract contract) {
                System.out.println("Processor");
                if (contract.getStatus() != SavingContractStatus.PROCEED) {
                    System.out.println("No Status");
                    return null;
                }

                TogetherRun togetherRun = togetherRunRepository.findBySavingContractId(contract.getSavingContractId()).orElse(null);
                if (togetherRun == null) {
                    System.out.println("No togetherRun");
                    return null;
                }

                TransferMoneyDTO transferMoneyDTO = TransferMoneyDTO.builder()
                        .fAccountId(togetherRun.getChildAccount())
                        .tAccountId(contract.getSavingAccount())
                        .amount(togetherRun.getChildContribute().intValue())
                        .build();
                StatusCode childTransferResult = accountService.transferMoney(transferMoneyDTO);

                transferMoneyDTO = TransferMoneyDTO.builder()
                        .fAccountId(togetherRun.getParentsAccount())
                        .tAccountId(contract.getSavingAccount())
                        .amount(togetherRun.getParentsContribute().intValue())
                        .build();
                StatusCode parentsTransferResult = accountService.transferMoney(transferMoneyDTO);

                if (childTransferResult == StatusCode.BAD_REQUEST || parentsTransferResult == StatusCode.BAD_REQUEST) {
                    System.out.println("Bad Request");
                    return null;
                }

                Relation relation = togetherRun.getRelation();
                SavingPayment childPayment = SavingPayment.builder()
                        .user(relation.getChild())
                        .depositAmount(togetherRun.getChildContribute())
                        .depositDate(LocalDateTime.now())
                        .savingContract(contract)
                        .build();
                System.out.println("Child Payment: " + childPayment);
                SavingPayment parentsPayment = SavingPayment.builder()
                        .user(relation.getParent())
                        .depositAmount(togetherRun.getParentsContribute())
                        .depositDate(LocalDateTime.now())
                        .savingContract(contract)
                        .build();
                System.out.println("Parents Payment: " + parentsPayment);

                return SavingTransferProcessorDto.builder()
                        .savingContractId(contract.getSavingContractId())
                        .childPayment(childPayment)
                        .parentsPayment(parentsPayment)
                        .build();
            }
        };
    }

    @Bean
    public ItemWriter<SavingTransferProcessorDto> paymentResultWriter(SavingContractRepository savingContractRepository, SavingPaymentRepository savingPaymentRepository) {
        return new ItemWriter<SavingTransferProcessorDto>() {
            @Override
            public void write(Chunk<? extends SavingTransferProcessorDto> chunk) throws Exception {
                System.out.println("Writer");
                List<? extends SavingTransferProcessorDto> items = chunk.getItems();
                SavingContract contract = null;
                for (SavingTransferProcessorDto item : items) {
                    try {
                        contract = savingContractRepository.findById(item.getSavingContractId()).orElse(null);
                        System.out.println("Saving Result: " + savingPaymentRepository.save(item.getChildPayment()));
                        System.out.println("Saving Result: " + savingPaymentRepository.save(item.getParentsPayment()));
                        contract.setCurrentAmount(contract.getCurrentAmount().add(item.getChildPayment().getDepositAmount()).add(item.getParentsPayment().getDepositAmount()));
                        System.out.println("Saved");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}