//package com.e201.kidswallet.togetherrun.test;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SavingTransferTestRunner implements ApplicationRunner {
//
//    private final JobLauncher jobLauncher;
//    private final Job savingTransferJob;
//
//    @Autowired
//    public SavingTransferTestRunner(JobLauncher jobLauncher, Job savingTransferJob) {
//        this.jobLauncher = jobLauncher;
//        this.savingTransferJob = savingTransferJob;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//
//        jobLauncher.run(savingTransferJob, jobParameters);
//    }
//}
//
