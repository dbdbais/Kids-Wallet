package com.e201.kidswallet.user.dto;

import com.e201.kidswallet.user.enums.Gender;
import com.e201.kidswallet.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequestDTO {
    private String userName;
    private String userPassword;
    private LocalDate userBirth;
    private Gender userGender;
    private String userRealName;
    private Role userRole;

    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userBirth=" + userBirth +
                ", userGender=" + userGender +
                ", userRealName='" + userRealName + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
