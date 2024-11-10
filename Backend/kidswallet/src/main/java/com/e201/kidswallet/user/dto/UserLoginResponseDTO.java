package com.e201.kidswallet.user.dto;

import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.enums.Gender;
import com.e201.kidswallet.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginResponseDTO {
    //성별, 이름, 돈, 롤, 카드 생성 했는지 여부, 통장 개설 여부, 등록한 아이나 어른 목록

    private StatusCode statusCode;

    private Long userId;

    private boolean hasCard;

    private String representAccountId;

    private String userName;

    private LocalDate userBirth;

    private Gender userGender;

    private String userRealName;

    private int userMoney;

    private Role userRole;

    private List<ParentChildResponseDTO> relations;

    public UserLoginResponseDTO(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public UserLoginResponseDTO(String userName, LocalDate userBirth, Gender userGender, String userRealName, int userMoney, Role userRole, List<ParentChildResponseDTO> parentsRelations) {
        this.userName = userName;
        this.userBirth = userBirth;
        this.userGender = userGender;
        this.userRealName = userRealName;
        this.userMoney = userMoney;
        this.userRole = userRole;
        this.relations = parentsRelations;
    }
}
