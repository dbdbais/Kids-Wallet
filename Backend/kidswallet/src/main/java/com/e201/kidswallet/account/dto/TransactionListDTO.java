package com.e201.kidswallet.account.dto;

import com.e201.kidswallet.common.exception.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class TransactionListDTO {
    StatusCode statusCode;
    List<TransactionResponseDTO> lst;

    public TransactionListDTO(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
