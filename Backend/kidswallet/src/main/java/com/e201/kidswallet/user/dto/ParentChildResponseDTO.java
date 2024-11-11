package com.e201.kidswallet.user.dto;

import com.e201.kidswallet.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParentChildResponseDTO {
    private Long userId;
    private String userName;
    private Gender userGender;
}
