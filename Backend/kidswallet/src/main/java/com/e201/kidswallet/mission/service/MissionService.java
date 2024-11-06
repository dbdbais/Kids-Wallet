package com.e201.kidswallet.mission.service;

import com.e201.kidswallet.mission.dto.*;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.enums.Status;
import com.e201.kidswallet.mission.repository.MissionRepository;
import com.e201.kidswallet.mission.dto.MissionListResponseDto;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.repository.RelationRepository;
import com.e201.kidswallet.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.repository.BegRepository;
import com.e201.kidswallet.common.exception.StatusCode;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class MissionService {
    private final long MAX_LONGBLOB_SIZE = 4_294_967_295L;
    private final BegRepository begRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    @Autowired
    public MissionService(BegRepository begRepository, MissionRepository missionRepository, UserRepository userRepository, RelationRepository relationRepository) {

        this.begRepository = begRepository;
        this.missionRepository = missionRepository;
        this.userRepository = userRepository;
        this.relationRepository = relationRepository;
    }

    //Transactional은 runtime err일 때만 롤백가능
    @Transactional
    public StatusCode begging(BeggingRequestDto beggingRequestDto){

        //사용자 ID를 사용하여 relation엔티티 조회
        List<Relation> relations =userRepository.findById(beggingRequestDto.getToUserId()).get().getParentsRelations();
        Relation relation=null;
        for(Relation r:relations){
            if(r.getParent().getUserId() == beggingRequestDto.getToUserId()) {
                relation = r;
                break;
            }
        }

        //TODO: 부모 없으면 예외처리

        Beg beg =Beg.builder()
                        .begContent(beggingRequestDto.getBeggingMessage())
                        .begMoney(beggingRequestDto.getBeggingMoney())
                        .relation(relation).build();

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

    @Transactional
    public List<MissionListResponseDto> getBegMissionList(long userId, long start, long end) {

        List<Relation> relations = relationRepository.findRelation(userId);
        List<MissionListResponseDto> missionListResponseDtos = new ArrayList<>();

        for(Relation r:relations){
            log.info("info: "+r.toString());
            List<Beg> begs = r.getBegs();
            log.info("begs.size(): "+begs.size());
            for(Beg beg:begs){
                if (beg instanceof HibernateProxy) {
                    Hibernate.initialize(beg);
                }
                Mission m = beg.getMission();

//                log.info("mission: "+m);
                // insert mission data
                MissionDto missionDto = new MissionDto(
                        m.getMissionId(),
                        m.getMissionStatus(),
                        m.getCompletionPhoto(),
                        m.getCompletedAt(),
                        m.getCreatedAt(),
                        m.getMissionContent(),
                        m.getDeadLine()
                );

                //insert beg data
                BegDto begDto = new BegDto(
                        beg.getBegId(),
                        beg.getBegMoney(),
                        beg.getBegContent(),
                        beg.getCreateAt(),
                        beg.getBegAccept()
                );

                missionListResponseDtos.add(new MissionListResponseDto(begDto,missionDto));

            }
        }
//        log.info("relationSize: " + relations.size());
        return missionListResponseDtos;
    }
}
