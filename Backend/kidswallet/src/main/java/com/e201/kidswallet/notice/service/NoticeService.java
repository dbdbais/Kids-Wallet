package com.e201.kidswallet.notice.service;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.notice.dto.NoticeDto;
import com.e201.kidswallet.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class NoticeService {

    private final RedisTemplate<String, Object> noticeRedisTemplate;
    private final UserRepository userRepository;

    @Autowired
    public NoticeService(RedisTemplate<String, Object> noticeRedisTemplate, UserRepository userRepository) {
        // RedisTemplate 설정: Jackson2JsonRedisSerializer로 직렬화
        Jackson2JsonRedisSerializer<NoticeDto> serializer = new Jackson2JsonRedisSerializer<>(NoticeDto.class);
        noticeRedisTemplate.setValueSerializer(serializer);
        this.noticeRedisTemplate = noticeRedisTemplate;
        this.userRepository = userRepository;
    }

    public String getUserName(long userId) {
        return userRepository.findById(userId).get().getUserName();
    }

    public List<NoticeDto> noticeDtoList(long userId) {
        String key = "notice:" + userId;
        List<Object> notices = noticeRedisTemplate.opsForList().range(key, 0, -1);

        if (notices == null || notices.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<NoticeDto> result = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper 객체 생성

            for (Object obj : notices) {
                // Object -> NoticeDto로 변환
                try {
                    NoticeDto noticeDto = objectMapper.convertValue(obj, NoticeDto.class);
                    result.add(noticeDto);
                } catch (Exception e) {
                    log.error("Error converting object to NoticeDto", e);
                }
            }
            return result;
        }
    }

    public StatusCode pushNotice(long userId, NoticeDto noticeDto) {
        String key = "notice:" + userId;
        String lockKey = key + ":lock";

        try {
            // 1. 락 획득
            boolean lockAcquired = tryAcquireLock(lockKey, 10, 5);
            if (!lockAcquired) {
                return StatusCode.FAILURE_LOCK; // 락을 얻지 못한 경우
            }

            // 2. Redis LIST에 데이터 추가
            noticeRedisTemplate.opsForList().rightPush(key, noticeDto);
            return StatusCode.SUCCESS;
        } finally {
            // 3. 락 해제
            releaseLock(lockKey);
        }
    }

    public StatusCode deleteNotice(long userId, int index) {
        // Redis에서 해당 사용자의 알림 삭제
        String key = "notice:" + userId;
        String lockKey = key + ":lock";

        try {
            // 1. 락 획득
            boolean lockAcquired = tryAcquireLock(lockKey, 10, 5);
            if (!lockAcquired) {
                return StatusCode.FAILURE_LOCK; // 락을 얻지 못한 경우
            }

            // 2. Redis에서 리스트 가져오기
            Long listSize = noticeRedisTemplate.opsForList().size(key);
            if (listSize == null || index >= listSize || index < 0) {
                return StatusCode.WRONG_INDEX; // 잘못된 인덱스
            }

            // 3. 해당 인덱스를 "DELETED" 같은 임시 값으로 설정
            noticeRedisTemplate.opsForList().set(key, index, "DELETED");

            // 4. "DELETED" 값을 제거
            noticeRedisTemplate.opsForList().remove(key, 0, "DELETED");

            return StatusCode.SUCCESS;
        } finally {
            // 5. 락 해제
            releaseLock(lockKey);
        }
    }

    // 락 획득 시도 (SETNX와 EXPIRE 조합)
    private boolean tryAcquireLock(String lockKey, long waitTime, long leaseTime) {
        // 락 키를 SETNX로 설정하고 TTL을 추가
        Boolean success = noticeRedisTemplate.opsForValue().setIfAbsent(lockKey, "locked", leaseTime, TimeUnit.SECONDS);
        return success != null && success;  // 락 획득 성공 여부 반환
    }

    // 락 해제
    private void releaseLock(String lockKey) {
        noticeRedisTemplate.delete(lockKey);  // 락 키 삭제
    }
}
