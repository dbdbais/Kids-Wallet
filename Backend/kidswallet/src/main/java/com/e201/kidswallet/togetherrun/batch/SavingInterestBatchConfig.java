package com.e201.kidswallet.togetherrun.batch;

import com.e201.kidswallet.togetherrun.entity.Saving;
import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractStatus;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import com.e201.kidswallet.togetherrun.repository.SavingRepository;
import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
import com.e201.kidswallet.togetherrun.service.TogetherRunService;
import lombok.NonNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SavingInterestBatchConfig {


    private int currentPage = 0;
    private final int chunkSize = 10;

    @Bean
    public Job savingInterestJob(JobRepository jobRepository, Step savingInterestStep) {
        return new JobBuilder("savingInterestJob", jobRepository)
                .start(savingInterestStep)
                .build();
    }

    @Bean
    public Step savingInterestStep(@Qualifier("kidswalletJobRepository") JobRepository jobRepository, PlatformTransactionManager transactionManager,
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
                if (currentChunk == null || currentChunk.isEmpty()) {
                    Pageable pageable = PageRequest.of(currentPage, chunkSize);
                    currentChunk = savingContractRepository.findByStatus(pageable);
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
    public ItemProcessor<SavingContract, SavingContract> interestProcessor(TogetherRunService togetherRunService) {
        return new ItemProcessor<SavingContract, SavingContract>() {
            @Override
            public SavingContract process(@NonNull SavingContract contract) {
                contract.setCurrentInterestAmount(togetherRunService.calcInterestAmount(contract));
                return contract;
            }
        };
    }

    @Bean
    public ItemWriter<SavingContract> interestResultWriter(SavingContractRepository savingContractRepository) {
        return new ItemWriter<SavingContract>() {
            @Override
            public void write(Chunk<? extends SavingContract> chunk) throws Exception {
                List<? extends SavingContract> items = chunk.getItems();
                for (SavingContract item : items) {
                    try {
                        if (item.getExpiredAt() != null && item.getExpiredAt().isEqual(LocalDate.now())) {
                            item.setStatus(SavingContractStatus.COMPLETED);
                            item.setDeletedAt(LocalDateTime.now());
                        }
                        savingContractRepository.save(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}