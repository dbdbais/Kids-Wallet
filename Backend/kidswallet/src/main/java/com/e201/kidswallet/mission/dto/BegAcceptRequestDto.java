package com.e201.kidswallet.mission.dto;

import lombok.*;

@Data
@Getter
@Setter
public class BegAcceptRequestDto {
    private long begId;
    //이거 왜 Boolean으로 해야지 정상적으로 받아와 짐? boolean이 안됨;;;
    private Boolean isAccept;

}
