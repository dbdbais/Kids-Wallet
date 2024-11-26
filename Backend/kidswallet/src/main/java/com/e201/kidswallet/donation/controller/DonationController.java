package com.e201.kidswallet.donation.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.donation.dto.DonateUserRequestDTO;
import com.e201.kidswallet.donation.dto.DonatedUserResponseDTO;
import com.e201.kidswallet.donation.dto.DonationRequestDTO;
import com.e201.kidswallet.donation.dto.DonationResponseDTO;
import com.e201.kidswallet.donation.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donation")
public class DonationController {

    private final DonationService donationService;

    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    /**
     *
     * @param donationRequestDTO
     * @return statusCode를 리턴한다.
     */

    @PostMapping("/regist")
    public ResponseEntity<ResponseDto> addDonation( @RequestBody DonationRequestDTO donationRequestDTO){
        StatusCode returnCode = donationService.saveDonation(donationRequestDTO);
        return ResponseDto.response(returnCode);
    }

    /**
     *
     * @param id 도네이션 ID
     * @return 해당하는 도네이션에 기부한 유저들을 전부 리턴한다.
     */

    @GetMapping("/list/user/{id}")
    public ResponseEntity<ResponseDto> fetchDonatedUsers (@PathVariable Long id){
        List<DonatedUserResponseDTO> donateUserList = donationService.getDonationUserList(id);
        return ResponseDto.response(StatusCode.SUCCESS,donateUserList);
    }

    /**
     * @return 기부 목록을 전부 리턴한다.
     */

    @GetMapping("/list")
    public ResponseEntity<ResponseDto> fetchDonation(){
        List<DonationResponseDTO> donationList = donationService.getDonationList();
        return ResponseDto.response(StatusCode.SUCCESS,donationList);
    }

    /**
     *
     * @param donateUserRequestDTO userId, donationId, money
     * @return 상태코드 리턴
     */

    @PostMapping("/donate")
    public ResponseEntity<ResponseDto> addUserToDonation(@RequestBody DonateUserRequestDTO donateUserRequestDTO){
        StatusCode returnCode = donationService.addDonateMoney(donateUserRequestDTO);
        return ResponseDto.response(returnCode);
    }









}
