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
public class SavingInterestTestRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job savingInterestJob;

    @Autowired
    public SavingInterestTestRunner(JobLauncher jobLauncher, Job savingInterestJob) {
        this.jobLauncher = jobLauncher;
        this.savingInterestJob = savingInterestJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        System.out.println("SavingInterestTestRunner");
        jobLauncher.run(savingInterestJob, jobParameters);
        System.out.println("SavingInterestTestRunner");
    }
}


