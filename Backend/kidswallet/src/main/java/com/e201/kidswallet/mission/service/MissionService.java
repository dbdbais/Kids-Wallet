package com.e201.kidswallet.mission.service;

import com.e201.kidswallet.mission.dto.*;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.enums.Status;
import com.e201.kidswallet.mission.repository.MissionRepository;
import lombok.extern.slf4j.Slf4j;
import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.repository.BegRepository;
import com.e201.kidswallet.common.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;


@Slf4j
@Service
public class MissionService {
    private final long MAX_LONGBLOB_SIZE = 4_294_967_295L;
    private final BegRepository begRepository;
    private final MissionRepository missionRepository;
    @Autowired
    public MissionService(BegRepository begRepository, MissionRepository missionRepository) {

        this.begRepository = begRepository;
        this.missionRepository = missionRepository;
    }

    //Transactional은 runtime err일 때만 롤백가능
    @Transactional
    public StatusCode begging(BeggingRequestDto beggingRequestDto){

//        TODO: 사용자 ID를 사용하여 relation엔티티 조회
//        Relation relation =null;

//        TODO: user,relation추가 하면 테스팅하기
//        Beg beg =Beg.builder()
//                        .begContent(beggingRequestDto.getBeggingMessage())
//                        .begMoney(beggingRequestDto.getBeggingMoney())
//                        .relation(relation).build();

        //임시 코드
        Beg beg =Beg.builder()
                .begContent(beggingRequestDto.getBeggingMessage())
                .begMoney(beggingRequestDto.getBeggingMoney())
                .build();

        log.info(beg.toString());
        //여기서 실패하면 jpa가 RUNTIMEEXCEPTION을 throw함 == transactional이 됨
        begRepository.save(beg);
        return StatusCode.SUCCESS;
    }

    @Transactional
    public StatusCode begAccept(BegAcceptRequestDto requestDto) {
        Beg beg = begRepository.findById(requestDto.getBegId()).get();
        begRepository.updateAccept(requestDto.getIsAccept(),requestDto.getBegId());
        return StatusCode.SUCCESS;
    }

    @Transactional
    public StatusCode assignMission(AssignMissionRequestDto requestDto) {
        Beg beg = begRepository.findById(requestDto.getBegId()).get();

        Mission mission = Mission.builder()
                .missionContent(requestDto.getMissionMessage())
                .deadLine(requestDto.getDeadline())
                .beg(beg)
                .build();

        missionRepository.save(mission);
        return StatusCode.SUCCESS;
    }

    @Transactional
    public StatusCode uploadCompleteImage(MissionCompleteRequestDto requestDto) {
        //base64를 byte배열로 인코딩
        byte[] imageBytes = Base64.getDecoder().decode(requestDto.getBase64Image());
        long missionId = requestDto.getMissionId();

        //TODO: SIZE CHECKING 로직 추가
        log.info("imageBytes.length: "+imageBytes.length);
        log.info("imageBytes.length > MAX_LONGBLOB_SIZE: "+ (imageBytes.length > MAX_LONGBLOB_SIZE));

        //image update
        missionRepository.uploadCompleteImage(imageBytes,missionId);
        return StatusCode.SUCCESS;

    }

    @Transactional
    public StatusCode missionCompleteCheck(MissionCompleteCheckRequestDto requestDto) {
        Status status;

        if(requestDto.getIsComplete()==true) {
            status = Status.complete;
            log.info("requestDto.isComplete: " + requestDto.getIsComplete());
            log.info("status: " + status);
        }
        else
            status=Status.fail;

//        missionRepository.updateMissionStatus(requestDto.getMissionId(),status.toString(),LocalDateTime.now());
        Mission mission = missionRepository.findById(requestDto.getMissionId()).get();
        mission.changeMissionStatusAndCompleteTime(status);

        log.info("Updating mission status: missionId=" + requestDto.getMissionId() + ", status=" + status + ", updateTime=" + LocalDateTime.now());
        //TODO: 계좌로 입금 로직 추가

        return StatusCode.SUCCESS;
    }

    public StatusCode getBegMissionList(String userId, long start, long end) {
        //TODO: 유저 ID를 사용하여 relation을 조회 후 beg 조회
        //TODO: 조회 된 Beg의 mission조회
        return StatusCode.SUCCESS;
    }
}
