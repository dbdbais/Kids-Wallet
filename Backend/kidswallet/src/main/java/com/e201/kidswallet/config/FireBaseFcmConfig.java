//package com.e201.kidswallet.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Configuration
//public class FireBaseFcmConfig {
//    @PostConstruct
//    public void init() {
////        try {
////            FileInputStream serviceAccount =
////                    new FileInputStream("src/main/resources/test-52949-firebase-adminsdk-thyfy-ad577119e5.json");
////
////            FirebaseOptions options = new FirebaseOptions.Builder()
////                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
////                    .build();
////
////            FirebaseApp.initializeApp(options);
////        }
////        catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        try{
//            InputStream serviceAccount = new ClassPathResource("test2-17181-firebase-adminsdk-pgcod-d8d2303a31.json").getInputStream();
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .build();
//
//            if (FirebaseApp.getApps().isEmpty()) { // FirebaseApp이 이미 초기화되어 있지 않은 경우에만 초기화 실행
//                FirebaseApp.initializeApp(options);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//}
