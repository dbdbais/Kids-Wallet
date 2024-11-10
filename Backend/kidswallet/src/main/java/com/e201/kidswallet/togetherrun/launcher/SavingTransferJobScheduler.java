//package com.e201.kidswallet.togetherrun.launcher;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//@Configuration
//@EnableScheduling
//public class SavingTransferJobScheduler {
//
//    private final JobLauncher jobLauncher;
//    private final Job savingTransferJob;
//
//    public SavingTransferJobScheduler(JobLauncher jobLauncher, Job savingTransferJob) {
//        this.jobLauncher = jobLauncher;
//        this.savingTransferJob = savingTransferJob;
//    }
//
//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
//    public void runAutomaticTransferJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//
//        jobLauncher.run(savingTransferJob, jobParameters);
//    }
//}
