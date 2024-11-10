package com.e201.kidswallet.donation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponseDTO {

    private String name;

    private String organName;

    private int target;

    private LocalDate targetDate;

    private String description;

}
