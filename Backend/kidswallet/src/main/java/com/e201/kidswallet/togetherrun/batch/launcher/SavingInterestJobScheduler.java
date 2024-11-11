package com.e201.kidswallet.togetherrun.batch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SavingInterestJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job savingInterestJob;

    public SavingInterestJobScheduler(JobLauncher jobLauncher, Job savingInterestJob) {
        this.jobLauncher = jobLauncher;
        this.savingInterestJob = savingInterestJob;
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void runSavingInterestJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(savingInterestJob, jobParameters);
    }
}
