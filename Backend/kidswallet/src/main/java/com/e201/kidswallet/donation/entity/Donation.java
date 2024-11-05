package com.e201.kidswallet.donation.entity;

import com.e201.kidswallet.donation.enums.DonationStatus;
import com.e201.kidswallet.user.enums.Gender;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity()
@Table(name="donation")
public class Donation{
    @Id
    @Column(name="donation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="donation_name")
    private String name;

    @Column(name = "donation_organizer")
    private String organ_name;

    @Column(name ="target_amount")
    private int target;

    @Column(name = "target_date")
    private LocalDateTime targetDate;

    @Column(name = "donation_description")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name="status",nullable = false)
    private DonationStatus donationStatus = DonationStatus.proceed;

    @CreatedDate
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="delete_at",nullable = true)
    private LocalDateTime deleteAt;

}
