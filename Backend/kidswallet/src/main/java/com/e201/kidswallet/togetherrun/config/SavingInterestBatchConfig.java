package com.e201.kidswallet.togetherrun.config;

import com.e201.kidswallet.togetherrun.dto.SavingInterestProcessorDto;
import com.e201.kidswallet.togetherrun.entity.Saving;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import com.e201.kidswallet.togetherrun.repository.SavingRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class SavingInterestBatchConfig {


    private int currentPage = 0;
    private final int chunkSize = 10;

    @Bean
    public Job savingInterestJob(JobRepository jobRepository, Step savingInterestStep) {
        System.out.println("savingInterestJob");
        return new JobBuilder("savingInterestJob", jobRepository)
                .start(savingInterestStep)
                .build();
    }

    @Bean
    public Step savingInterestStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   ItemReader<SavingContract> allContractReader,
                                   ItemProcessor<SavingContract, SavingContract> interestProcessor,
                                   ItemWriter<SavingContract> interestResultWriter) {
        return new StepBuilder("step", jobRepository).<SavingContract, SavingContract>chunk(10, transactionManager)
                .reader(allContractReader)
                .processor(interestProcessor)
                .writer(interestResultWriter)
                .build();
    }

    @Bean
    public ItemReader<SavingContract> allContractReader(SavingContractRepository savingContractRepository) {
        return new ItemReader<SavingContract>() {
            private List<SavingContract> currentChunk = null;

            @Override
            public SavingContract read() {
                System.out.println("Reader read");

                if (currentChunk == null || currentChunk.isEmpty()) {
                    Pageable pageable = PageRequest.of(currentPage, chunkSize);
                    Page<SavingContract> page = savingContractRepository.findAll(pageable);
                    currentChunk = new ArrayList<>(page.getContent());
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
    public ItemProcessor<SavingContract, SavingContract> interestProcessor(SavingContractRepository savingContractRepository, SavingRepository savingRepository, TogetherRunRepository togetherRunRepository) {
        return new ItemProcessor<SavingContract, SavingContract>() {
            @Override
            public SavingContract process(@NonNull SavingContract contract) {
                System.out.println("interestProcessor: " + contract);

                Saving saving = savingRepository.findById(contract.getSaving().getSavingId()).orElse(null);
                BigDecimal interestRate = saving.getInterestRate();

                LocalDate startDate = contract.getCreatedAt().toLocalDate();
                LocalDate endDate = contract.getExpiredAt();
                int days = endDate.compareTo(startDate);

                TogetherRun togetherRun = togetherRunRepository.findBySavingContractId(contract.getSavingContractId()).orElse(null);
                BigDecimal targetAmount = togetherRun.getTargetAmount();

                BigDecimal interestAmount = targetAmount.multiply(interestRate).divide(BigDecimal.valueOf(365), 2).multiply(BigDecimal.valueOf(days));
                // target_amount * interest rate / 365 * (expired_at - created_at)
                contract.setCurrentInterestAmount(interestAmount);
                return contract;
            }
        };
    }

    @Bean
    public ItemWriter<SavingContract> interestResultWriter(SavingContractRepository savingContractRepository) {
        return new ItemWriter<SavingContract>() {
            @Override
            public void write(Chunk<? extends SavingContract> chunk) throws Exception {
                System.out.println("Writer");
                List<? extends SavingContract> items = chunk.getItems();
                for (SavingContract item : items) {
                    try {
                        System.out.println("Saving Result: " + savingContractRepository.save(item));
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