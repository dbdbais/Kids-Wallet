//package com.e201.kidswallet.fcm;
//
//import com.e201.kidswallet.AbstractTest;
//import com.e201.kidswallet.common.exception.StatusCode;
//import com.e201.kidswallet.fcm.dto.FcmTokenRequestDto;
//import com.e201.kidswallet.fcm.service.FcmService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class FcmTest extends AbstractTest {
//
//    @Autowired
//    FcmService fcmService;
//
//    @Test
//    public void pushTokenInRedis(){
//        FcmTokenRequestDto requestDto = new FcmTokenRequestDto(1L,"cHoQ5ExZTjy6OUjR5lJY19:APA91bG0ZKysLox7BtElV657-5JlK333EMI1s97URrIgMJoiq6e7pCDYr-g-Z6gcYaquQrs3yBwvPmVMjYZyVMFvHOCgFSdnh2VsZUjMJi86zlOlYi20sM8", LocalDateTime.now());
//        assertEquals(StatusCode.SUCCESS,fcmService.tokenToRedis(requestDto));
//    }
//}
