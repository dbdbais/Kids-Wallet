package com.e201.kidswallet.donation.service;

import com.e201.kidswallet.donation.dto.DonationResponseDTO;
import com.e201.kidswallet.donation.entity.Donation;
import com.e201.kidswallet.donation.repository.DonationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DonationService {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

}
