package com.e201.kidswallet.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MonthlyExpenseDTO {
    private int curSpentMoney;   // 지출 금액
    private int curIncomeMoney;  // 수입 금액

    private int prevSpentMoney;
    private int prevIncomeMoney;

    private int[] prevListSpent = new int[7]; // 이전 주 금액 월화수목금토일 저장
    private int[] curListSpent = new int[7]; // 이전 주 금액 월화수목금토일 저장

    private int[] prevListIncome = new int[7]; // 현재 주 금액 월화수목금토일 저장
    private int[] curListIncome = new int[7]; // 현재 주 금액 월화수목금토일 저장
}
