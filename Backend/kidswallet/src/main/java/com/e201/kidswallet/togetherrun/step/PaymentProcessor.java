//package com.e201.kidswallet.togetherrun.step;
//
//import com.e201.kidswallet.account.dto.TransferMoneyDTO;
//import com.e201.kidswallet.account.service.AccountService;
//import com.e201.kidswallet.common.exception.StatusCode;
//import com.e201.kidswallet.togetherrun.dto.SavingTransferProcessorDto;
//import com.e201.kidswallet.togetherrun.entity.SavingContract;
//import com.e201.kidswallet.togetherrun.entity.SavingContractStatus;
//import com.e201.kidswallet.togetherrun.entity.SavingPayment;
//import com.e201.kidswallet.togetherrun.entity.TogetherRun;
//import com.e201.kidswallet.togetherrun.repository.TogetherRunRepository;
//import com.e201.kidswallet.user.entity.Relation;
//import com.e201.kidswallet.user.entity.User;
//import lombok.NonNull;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PaymentProcessor implements ItemProcessor<SavingContract, SavingTransferProcessorDto> {
//
//    private final TogetherRunRepository togetherRunRepository;
//    private final AccountService accountService;
//
//    public PaymentProcessor(TogetherRunRepository togetherRunRepository, AccountService accountService) {
//        this.togetherRunRepository = togetherRunRepository;
//        this.accountService = accountService;
//    }
//
//    @Override
//    public SavingTransferProcessorDto process(@NonNull SavingContract contract) {
//        System.out.println("Processor");
//        if (contract.getStatus() != SavingContractStatus.PROCEED) {
//            System.out.println("No Status");
//            return null;
//        }
//
//        TogetherRun togetherRun = togetherRunRepository.findBySavingContractId(contract.getSavingContractId()).orElse(null);
//        if (togetherRun == null) {
//            System.out.println("No togetherRun");
//            return null;
//        }
//
//        TransferMoneyDTO transferMoneyDTO = TransferMoneyDTO.builder()
//                .fAccountId(togetherRun.getChildAccount())
//                .tAccountId(contract.getSavingAccount())
//                .amount(togetherRun.getChildContribute().intValue())
//                .build();
//        StatusCode childTransferResult = accountService.transferMoney(transferMoneyDTO);
//        transferMoneyDTO = TransferMoneyDTO.builder()
//                .fAccountId(togetherRun.getParentsAccount())
//                .tAccountId(contract.getSavingAccount())
//                .amount(togetherRun.getParentsContribute().intValue())
//                .build();
//        StatusCode parentsTransferResult = accountService.transferMoney(transferMoneyDTO);
//        if (childTransferResult == StatusCode.BAD_REQUEST || parentsTransferResult == StatusCode.BAD_REQUEST) {
//            System.out.println("Bad Request");
//            return null;
//        }
//
//        Relation relation = togetherRun.getRelation();
//        SavingPayment childPayment = SavingPayment.builder()
//                .user(relation.getChild())
//                .depositAmount(togetherRun.getChildContribute())
//                .depositDate(LocalDateTime.now())
//                .savingContract(contract)
//                .build();
//        System.out.println("Child Payment: " + childPayment);
//        SavingPayment parentsPayment = SavingPayment.builder()
//                .user(relation.getParent())
//                .depositAmount(togetherRun.getParentsContribute())
//                .depositDate(LocalDateTime.now())
//                .savingContract(contract)
//                .build();
//        System.out.println("Parents Payment: " + parentsPayment);
//
//        return SavingTransferProcessorDto.builder()
//                .childPayment(childPayment)
//                .parentsPayment(parentsPayment)
//                .build();
//    }
//}
