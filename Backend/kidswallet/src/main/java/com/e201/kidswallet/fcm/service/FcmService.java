package com.e201.kidswallet.fcm.service;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.fcm.dto.FcmTokenRequestDto;
import com.e201.kidswallet.mission.enums.Status;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class FcmService {

    private final RedisTemplate<String, String> fcmRedisTemplate;

    @Autowired
    public FcmService(@Qualifier("fcmRedisTemplate")RedisTemplate<String, String> fcmRedisTemplete) {
        this.fcmRedisTemplate = fcmRedisTemplete;
    }

    //Front로 부터 FCM 토큰을 받아 redis에 저장 하는 로직
    public StatusCode tokenToRedis(FcmTokenRequestDto requestDto) {
        // Redis 키 구성: transaction:계좌번호:트랜잭션ID
        String key = "fcmToken:" + requestDto.getUserId();
        fcmRedisTemplate.opsForValue().set(key, requestDto.getTokenValue(), Duration.ofDays(365));
        return StatusCode.SUCCESS;
    }

    //get FCMtoken in redis
    public String getToken(long toUserId){
        String key = "fcmToken:" + toUserId;
        String token = fcmRedisTemplate.opsForValue().get(key);
        if (token != null) {
            // 키가 존재하면 TTL을 1년(365일)로 초기화
            fcmRedisTemplate.expire(key, Duration.ofDays(365));
        }
        return token;
    }

    public StatusCode sendMessage(String token, String title, String body) {
        if (token == null) {
            log.error("Token is null");
            return StatusCode.TOKEN_IS_NULL;
        }
        try{
            String message = FirebaseMessaging.getInstance().send(Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setToken(token)  // 대상 디바이스의 등록 토큰
                    .build());
        }
        catch (FirebaseMessagingException e){
            log.error(e.getMessage());
            return StatusCode.BAD_REQUEST;
        }
        return StatusCode.SUCCESS;

    }

}
