package com.e201.kidswallet.mission.service;

import com.e201.kidswallet.mission.dto.*;
import com.e201.kidswallet.mission.entity.Mission;
import com.e201.kidswallet.mission.enums.Status;
import com.e201.kidswallet.mission.repository.MissionRepository;
import com.e201.kidswallet.mission.dto.MissionListResponseDto;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.enums.Role;
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
    //    private final long MAX_LONGBLOB_SIZE = 4_294_967_295L;

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
        List<Relation> relations = relationRepository.findRelation(beggingRequestDto.getToUserId());

        //부모가 없을 때 == 아무런 관계가 없을 때 예외 처리
        if(relations == null || relations.isEmpty())
            return StatusCode.NO_PARENTS;

        // 부모 userId로 relation 찾기
        Relation relation=null;
        for(Relation r:relations){
            if(r.getParent().getUserId() == beggingRequestDto.getToUserId()) {
                relation = r;
                break;
            }
        }

        //Beg테이블 빌드
        Beg beg =Beg.builder()
                .begContent(beggingRequestDto.getBeggingMessage())
                .begMoney(beggingRequestDto.getBeggingMoney())
                .relation(relation).build();

        log.info(beg.toString());
        //여기서 실패하면 jpa가 RUNTIMEEXCEPTION을 throw함 == transactional이 됨
        begRepository.save(beg);
        return StatusCode.SUCCESS;
    }

    // 조르기를 부모가 허락 or 반대
    @Transactional
    public StatusCode begAccept(BegAcceptRequestDto requestDto) {
        Beg beg = begRepository.findById(requestDto.getBegId()).get();
        begRepository.updateAccept(requestDto.getIsAccept(),requestDto.getBegId());
        return StatusCode.SUCCESS;
    }

    // 미션 부여
    @Transactional
    public StatusCode assignMission(AssignMissionRequestDto requestDto) {
        Beg beg = begRepository.findById(requestDto.getBegId()).get();

        //미션엔티티 빌드
        Mission mission = Mission.builder()
                .missionContent(requestDto.getMissionMessage())
                .beg(beg)
                .build();

        missionRepository.save(mission);
        return StatusCode.SUCCESS;
    }

    // 이미지 업로드 == 미션에 대한 이미지
    @Transactional
    public StatusCode uploadCompleteImage(MissionCompleteRequestDto requestDto) {
        final long MAX_LONGBLOB_SIZE = 4L * 1024 * 1024 * 1024; // 4GB
        //base64를 byte배열로 인코딩
        byte[] imageBytes = Base64.getDecoder().decode(requestDto.getBase64Image());
        long missionId = requestDto.getMissionId();

        // IMAGE SIZE CHECKING
        if(imageBytes.length > MAX_LONGBLOB_SIZE) {
            return StatusCode.OVERFLOW_IMAGE_SIZE;
        }
        log.info("imageBytes.length: "+imageBytes.length);
        log.info("imageBytes.length > MAX_LONGBLOB_SIZE: "+ (imageBytes.length > MAX_LONGBLOB_SIZE));

        //image update
        missionRepository.uploadCompleteImage(imageBytes,missionId,Status.submit.toString());
        return StatusCode.SUCCESS;

    }

    // 부모가 자식이 보낸 image를 보고 미션
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

        return StatusCode.SUCCESS;
    }

    //TODO: 지연로딩을 위한 처리 해야됨
    //조르기와 미션 리스트 get
    @Transactional
    public List<MissionListResponseDto> getBegMissionList(long userId, long start, long end) {

        //userId로 user와 관련된 관계들을 get
        List<Relation> relations = relationRepository.findRelation(userId);
        Role role = userRepository.findById(userId).get().getUserRole();
        List<MissionListResponseDto> missionListResponseDtos = new ArrayList<>();

        // 관계들을 순회하며 관계와 관련된 Beg들을 get
        for (Relation r : relations) {
            // 요청하는 주체의 role에 따라 name을 동적으로 값 설정
            // 요청하는 주체가 아이 => parent realName get in relation
            // 요청하는 주체가 어른 => child realName get in relation
            String name = null;
            if (role == Role.CHILD)
                name = r.getParent().getUserRealName();
            else
                name = r.getChild().getUserRealName();

            log.info("info: " + r.toString());
            List<Beg> begs = r.getBegs();
            log.info("begs.size(): "+begs.size());
            //Beg들을 순회하며 Mision을 찾음 (1:1 관계)
            for(Beg beg:begs){
                if (beg instanceof HibernateProxy) {
                    Hibernate.initialize(beg);
                }
                Mission m = beg.getMission();

                // insert mission data
                MissionDto missionDto;
                if(m!=null){ // 조르기는 있어도 미션이 없을 수도 있기 때문에 예외처리
                    //base64로 인코딩
                    String base64String;
                    if(m.getCompletionPhoto()==null) {
                        base64String=null;
                    }
                    else{
                        base64String = Base64.getEncoder().encodeToString(m.getCompletionPhoto());
                    }
                    missionDto = new MissionDto(
                            m.getMissionId(),
                            m.getMissionStatus(),
                            base64String,
                            m.getCompletedAt(),
                            m.getCreatedAt(),
                            m.getMissionContent()
                    );
                }
                else{
                    missionDto = new MissionDto();
                }

                //insert beg data
                BegDto begDto = new BegDto(
                        beg.getBegId(),
                        beg.getBegMoney(),
                        beg.getBegContent(),
                        beg.getCreateAt(),
                        beg.getBegAccept()
                );

                missionListResponseDtos.add(new MissionListResponseDto(name,begDto,missionDto));

            }
        }
        return missionListResponseDtos;
    }

    public long UseBegIdGetChildId(long begId) {
        long childId = begRepository.findById(begId).get().getRelation().getChild().getUserId();
        log.info("childId: " + childId);
        return childId;
    }

    public long UseMissionIdGetParentId(long missionId) {
        long parentId = missionRepository.findById(missionId).get().getBeg().getRelation().getParent().getUserId();
        log.info("parentId: " + parentId);
        return parentId;
    }

    public long UseMissionIdGetChildId(long missionId) {
        long childId = missionRepository.findById(missionId).get().getBeg().getRelation().getChild().getUserId();
        log.info("childId: " + childId);
        return childId;
    }
}
