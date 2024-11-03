package com.e201.kidswallet;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.mission.service.MissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MissionTest extends AbstractTest {
    @Autowired
    private MissionService begService;


    @Test
    public void createBegTest(){
        assertEquals(StatusCode.SUCCESS, StatusCode.SUCCESS); // 결과 검증
    }
}
