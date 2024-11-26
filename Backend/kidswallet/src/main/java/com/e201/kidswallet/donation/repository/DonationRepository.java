package com.e201.kidswallet.donation.repository;

import com.e201.kidswallet.donation.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation,Long> {
}
