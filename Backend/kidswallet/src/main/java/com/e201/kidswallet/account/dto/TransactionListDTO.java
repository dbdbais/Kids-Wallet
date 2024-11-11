package com.e201.kidswallet.account.dto;

import com.e201.kidswallet.common.exception.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionListDTO {
    private StatusCode statusCode;
    private List<TransactionResponseDTO> lst;

    public TransactionListDTO(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
