package com.e201.kidswallet.user.dto;

import com.e201.kidswallet.common.exception.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class UserLoginResponseDTO {
    StatusCode statusCode;
    Map<String,Long> map;

    public UserLoginResponseDTO(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.map = null;
    }
}
