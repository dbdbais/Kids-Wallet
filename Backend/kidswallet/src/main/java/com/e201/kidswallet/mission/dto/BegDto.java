package com.e201.kidswallet.mission.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BegDto {
    private Long begId;
    private int begMoney;
    private String begContent;
    private LocalDateTime createAt;
    private Boolean begAccept;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BegDto begDto = (BegDto) o;
        return begMoney == begDto.begMoney &&
                Objects.equals(begId, begDto.begId) &&
                Objects.equals(begContent, begDto.begContent) &&
                Objects.equals(begAccept, begDto.begAccept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begId, begMoney, begContent, begAccept);
    }
}
