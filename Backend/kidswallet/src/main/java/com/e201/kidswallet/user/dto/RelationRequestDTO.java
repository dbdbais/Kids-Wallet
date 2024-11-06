package com.e201.kidswallet.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RelationRequestDTO {
    String childName;
    String parentName;
}
