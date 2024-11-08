package com.e201.kidswallet.togetherrun.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job automaticTransferJob;

    public ScheduledJobLauncher(JobLauncher jobLauncher, Job automaticTransferJob) {
        this.jobLauncher = jobLauncher;
        this.automaticTransferJob = automaticTransferJob;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void runAutomaticTransferJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(automaticTransferJob, jobParameters);
    }
}
