package com.e201.kidswallet.mission;

import com.e201.kidswallet.AbstractTest;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.mission.dto.*;
import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.repository.MissionRepository;
import com.e201.kidswallet.mission.service.MissionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MissionTest extends AbstractTest {
    @Autowired
    private MissionService service;
    @Autowired
    private MissionRepository missionRepository;

    @Test
    @Order(1)
//    @Transactional
    public void createBegTest(){
        BeggingRequestDto begRequestDto = new BeggingRequestDto(1,"Create Beg Test",1000);
        assertEquals(StatusCode.SUCCESS, service.begging(begRequestDto)); // 결과 검증
    }

    @Test
    @Order(2)
    public void begAccept(){
        BegAcceptRequestDto requestDto = new BegAcceptRequestDto(1,true);
        assertEquals(StatusCode.SUCCESS, service.begAccept(requestDto));

    }

    //해결:OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
    //https://hyunrian.tistory.com/76
    // run -> edit configurations -> junit -> build and run에서 -Xshare:off 추가

    @Test
    @Order(3)
    public void assignMission(){
        try{
            missionRepository.findById(1L);
            AssignMissionRequestDto requestDto = new AssignMissionRequestDto(1,"Assign Mission Test", LocalDateTime.now());
            assertEquals(StatusCode.SUCCESS, service.assignMission(requestDto));
        }
        catch (DataIntegrityViolationException e){
            e.printStackTrace();
            assertEquals(DataIntegrityViolationException.class,e.getClass());
        }
    }

    @Test
    public void uploadCompleteImage(){
        String base64Image=encodingBase64("C:\\Users\\SSAFY\\Pictures\\jenkins-svgrepo-com.png");
        MissionCompleteRequestDto missionCompleteRequestDto = new MissionCompleteRequestDto(1,base64Image);
        assertEquals(StatusCode.SUCCESS,service.uploadCompleteImage(missionCompleteRequestDto));
    }

    public String encodingBase64(String imgUrl){
        try {
            // 이미지 파일 경로
            File file = new File(imgUrl); // 실제 이미지 경로로 변경
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(fileContent);
            String metadata="data:image/png;base64,";
            return base64Image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void completeMissionCheck(){
        MissionCompleteCheckRequestDto requestDto = new MissionCompleteCheckRequestDto(1L,false);
        StatusCode result = service.missionCompleteCheck(requestDto);
        System.out.println(missionRepository.findById(1L).get().getMissionStatus());

        assertEquals(StatusCode.SUCCESS,result);




    }
}
