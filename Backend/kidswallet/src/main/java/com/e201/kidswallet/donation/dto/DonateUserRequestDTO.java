package com.e201.kidswallet.donation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DonateUserRequestDTO {
    Long userId;
    Long donationId;
    int money;
}
