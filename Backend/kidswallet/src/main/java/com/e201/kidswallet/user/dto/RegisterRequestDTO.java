package com.e201.kidswallet.user.dto;

import com.e201.kidswallet.user.enums.Gender;
import com.e201.kidswallet.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class RegisterRequestDTO {
    private String userName;
    private String userPassword;
    private LocalDate userBirth;
    private String userEmail;
    private Gender userGender;
    private String userRealName;
    private Role userRole;
}
