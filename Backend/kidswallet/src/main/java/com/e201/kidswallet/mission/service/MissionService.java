package com.e201.kidswallet.mission.service;

import com.e201.kidswallet.mission.dto.AssignMissionRequestDto;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.repository.MissionRepository;
import lombok.extern.slf4j.Slf4j;
import com.e201.kidswallet.mission.dto.BegAcceptRequestDto;
import com.e201.kidswallet.mission.dto.BeggingRequestDto;
import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.repository.BegRepository;
import com.e201.kidswallet.common.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class MissionService {


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
}
