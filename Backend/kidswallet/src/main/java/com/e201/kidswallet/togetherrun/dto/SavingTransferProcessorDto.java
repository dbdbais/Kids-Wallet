package com.e201.kidswallet.togetherrun.dto;

import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SavingTransferProcessorDto {
    private SavingPayment childPayment;
    private SavingPayment parentsPayment;
}
