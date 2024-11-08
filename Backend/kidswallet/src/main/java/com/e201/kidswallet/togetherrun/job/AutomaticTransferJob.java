package com.e201.kidswallet.togetherrun.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batch.model.DepositAccount;
import com.example.batch.model.TransferResult;

@Configuration
public class AutomaticTransferJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemReader<DepositAccount> accountReader;
    private final ItemProcessor<DepositAccount, TransferResult> accountProcessor;
    private final ItemWriter<TransferResult> transferWriter;

    public AutomaticTransferJob(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            ItemReader<DepositAccount> accountReader,
            ItemProcessor<DepositAccount, TransferResult> accountProcessor,
            ItemWriter<TransferResult> transferWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.accountReader = accountReader;
        this.accountProcessor = accountProcessor;
        this.transferWriter = transferWriter;
    }

    @Bean
    public Job createAutomaticTransferJob() {
        return jobBuilderFactory.get("automaticTransferJob")
                .start(createTransferFundsStep())
                .build();
    }

    @Bean
    public Step createTransferFundsStep() {
        return stepBuilderFactory.get("transferFundsStep")
                .<DepositAccount, TransferResult>chunk(10)
                .reader(accountReader)
                .processor(accountProcessor)
                .writer(transferWriter)
                .build();
    }
}
