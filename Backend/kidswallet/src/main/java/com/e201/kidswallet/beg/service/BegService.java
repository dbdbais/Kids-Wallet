package com.e201.kidswallet.beg.service;

import com.e201.kidswallet.beg.dto.BeggingRequestDto;
import com.e201.kidswallet.beg.entity.Beg;
import com.e201.kidswallet.beg.repository.BegRepository;
import com.e201.kidswallet.common.exception.RestApiException;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BegService {


    private BegRepository begRepository;

    @Autowired
    public BegService(BegRepository begRepository) {
        this.begRepository = begRepository;
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

        //여기서 실패하면 jpa가 RUNTIMEEXCEPTION을 throw함 == transactional이 됨
        begRepository.save(beg);
        return StatusCode.SUCCESS;
    }






}
