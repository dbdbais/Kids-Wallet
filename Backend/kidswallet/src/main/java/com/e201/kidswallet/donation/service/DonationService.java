package com.e201.kidswallet.donation.service;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.donation.dto.DonateUserRequestDTO;
import com.e201.kidswallet.donation.dto.DonatedUserResponseDTO;
import com.e201.kidswallet.donation.dto.DonationRequestDTO;
import com.e201.kidswallet.donation.dto.DonationResponseDTO;
import com.e201.kidswallet.donation.entity.Donation;
import com.e201.kidswallet.donation.repository.DonationRepository;
import com.e201.kidswallet.user.UserRepository;
import com.e201.kidswallet.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    public StatusCode saveDonation(DonationRequestDTO donationRequestDTO) {

        Donation donation = Donation.builder()
                .name(donationRequestDTO.getName())
                .organName(donationRequestDTO.getOrganName())
                .target(donationRequestDTO.getTarget())
                .targetDate(donationRequestDTO.getTargetDate())
                .description(donationRequestDTO.getDescription())
                .build();

        try {
            donationRepository.save(donation);
        } catch (Exception e) {
            log.info(e.getMessage());
            return StatusCode.BAD_REQUEST;
        }
        return StatusCode.SUCCESS;
    }

    public List<DonatedUserResponseDTO> getDonationUserList(Long id) {
        Donation sDonation = donationRepository.findById(id).orElse(null);

        if (sDonation == null) {
            return new ArrayList<>(); // 빈 리스트 반환
        }

        List<DonatedUserResponseDTO> dLst = new ArrayList<>();

        for (User u : sDonation.getUserMap().keySet()) {
            dLst.add(
                    DonatedUserResponseDTO.builder()
                            .userName(u.getUserName())
                            .userBirthDay(u.getUserBirth())
                            .userGender(u.getUserGender())
                            .donatePrice(sDonation.getUserMap().get(u))
                            .build()
            );

        }

        Collections.sort(dLst);

        return dLst;

    }

    public StatusCode addDonateMoney(DonateUserRequestDTO donateUserRequestDTO) {
        User sUser = userRepository.findById(donateUserRequestDTO.getUserId()).orElse(null);
        Donation sDonation = donationRepository.findById(donateUserRequestDTO.getDonationId()).orElse(null);

        if (sUser == null || sDonation == null) {
            return StatusCode.BAD_REQUEST;
        }

        int money = donateUserRequestDTO.getMoney();
        int mxDonation = sDonation.getTarget();

        Map<User, Integer> sUserMap = sDonation.getUserMap();

        if (sUserMap.containsKey(sUser)) {
            //이미있다면

            // sUserMap.put(sUser,Math.min(curDonation + money,mxDonation));
            // UserMap에 더한다.
            sUserMap.compute(sUser, (k, curDonation) -> Math.min(curDonation + money, mxDonation));

        } else {
            //없다면 Map에 추가
            sUserMap.put(sUser, Math.min(money, mxDonation));
        }

        return StatusCode.SUCCESS;
    }

    public List<DonationResponseDTO> getDonationList(){
        List<DonationResponseDTO> dLst = new ArrayList<>();

        List<Donation> lst = donationRepository.findAll();

        for(Donation d : lst){
            dLst.add(DonationResponseDTO.builder()
                            .name(d.getName())
                            .organName(d.getOrganName())
                            .target(d.getTarget())
                            .targetDate(d.getTargetDate())
                            .description(d.getDescription()).build());
        }

        return dLst;

    }

}
