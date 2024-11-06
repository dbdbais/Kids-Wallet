package com.e201.kidswallet.donation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DonationRequestDTO {
    private String name;
    private String organName;
    private Integer target;
    private LocalDate targetDate;
    private String description;

    @Override
    public String toString() {
        return "DonationRequestDTO{" +
                "name='" + name + '\'' +
                ", organName='" + organName + '\'' +
                ", target=" + target +
                ", targetDate=" + targetDate +
                ", description='" + description + '\'' +
                '}';
    }
}
