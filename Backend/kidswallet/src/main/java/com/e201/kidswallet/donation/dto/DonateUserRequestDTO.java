package com.e201.kidswallet.donation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DonateUserRequestDTO {
    Long userId;
    Long donationId;
    int money;
}
