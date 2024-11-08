package com.e201.kidswallet.togetherrun.config;

import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.togetherrun.dto.SavingTransferProcessorDto;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.togetherrun.step.ContractReader;
import com.e201.kidswallet.togetherrun.step.PaymentProcessor;
import com.e201.kidswallet.togetherrun.step.PaymentResultWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SavingTransferBatchConfig {

    @Bean
    public Job savingTransferJob(JobRepository jobRepository, Step savingTransferStep) {
        return new JobBuilder("savingTransferJob", jobRepository)
                .start(savingTransferStep)
                .build();
    }

    @Bean
    public Step savingTransferStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                    ItemReader<SavingContract> contractReader,
                                    ItemProcessor<SavingContract, SavingTransferProcessorDto> paymentProcessor,
                                    ItemWriter<SavingTransferProcessorDto> paymentResultWriter) {
        return new StepBuilder("step", jobRepository).<SavingContract, SavingTransferProcessorDto>chunk(10, transactionManager)
                .reader(contractReader)
                .processor(paymentProcessor)
                .writer(paymentResultWriter)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public ItemReader<SavingContract> contractReader(SavingContractRepository savingContractRepository) {
        return new ContractReader(savingContractRepository);
    }

    @Bean
    public ItemProcessor<SavingContract, SavingTransferProcessorDto> paymentProcessor(TogetherRunRepository togetherRunRepository, AccountService accountService) {
        return new PaymentProcessor(togetherRunRepository, accountService);
    }

    @Bean
    public ItemWriter<SavingTransferProcessorDto> paymentResultWriter(SavingPaymentRepository savingPaymentRepository) {
        return new PaymentResultWriter(savingPaymentRepository);
    }
}