package com.e201.kidswallet.donation.dto;

import com.e201.kidswallet.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DonatedUserResponseDTO implements Comparable<DonatedUserResponseDTO> {
    private String userName;
    private LocalDate userBirthDay;
    private Gender userGender;
    private int donatePrice;


    @Override
    public int compareTo(DonatedUserResponseDTO o) {

        if(donatePrice == o.donatePrice){
            return userName.compareTo(o.userName);
        }
        return Integer.compare(donatePrice,o.donatePrice) * -1;
    }
}
