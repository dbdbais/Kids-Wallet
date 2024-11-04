package com.e201.kidswallet.donation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class DonationResponseDTO {

    private String name;

    private String organ_name;

    private int target;

    private LocalDateTime targetDate;

    private String description;
}
