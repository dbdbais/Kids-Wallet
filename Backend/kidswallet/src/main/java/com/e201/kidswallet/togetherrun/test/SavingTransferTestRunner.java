package com.e201.kidswallet.togetherrun.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SavingTransferTestRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job savingTransferJob;

    @Autowired
    public SavingTransferTestRunner(JobLauncher jobLauncher, Job savingTransferJob) {
        this.jobLauncher = jobLauncher;
        this.savingTransferJob = savingTransferJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // JobParameters 설정 (필요 시 날짜나 고유값 추가)
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // 유니크 파라미터 추가
                .toJobParameters();

        // Job 실행
        jobLauncher.run(savingTransferJob, jobParameters);
    }
}

