package com.e201.kidswallet.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserLoginDTO {
    private String userName;
    private String userPassword;
}
