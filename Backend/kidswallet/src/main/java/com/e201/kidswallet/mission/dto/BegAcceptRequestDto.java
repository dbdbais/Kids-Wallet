package com.e201.kidswallet.mission.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BegAcceptRequestDto {
    private long begId;
    private Boolean isAccept;

}
