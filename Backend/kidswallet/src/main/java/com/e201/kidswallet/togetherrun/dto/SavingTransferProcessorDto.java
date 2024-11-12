package com.e201.kidswallet.togetherrun.dto;

import com.e201.kidswallet.togetherrun.entity.SavingPayment;
import com.e201.kidswallet.togetherrun.entity.enums.SavingContractPaymentCheck;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SavingTransferProcessorDto {
    private long savingContractId;
    private SavingPayment childPayment;
    private SavingPayment parentsPayment;
    private SavingContractPaymentCheck paymentCheck;
    private boolean paymentSuccess;
}
