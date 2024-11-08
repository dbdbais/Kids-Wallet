package com.e201.kidswallet.togetherrun.step;

import com.e201.kidswallet.togetherrun.entity.SavingContract;
import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

public class ContractReader implements ItemReader<SavingContract> {

    private final SavingContractRepository savingContractRepository;
    private ListItemReader<SavingContract> delegate;

    public ContractReader(SavingContractRepository savingContractRepository) {
        this.savingContractRepository = savingContractRepository;
    }

    @PostConstruct
    public void init() {
        List<SavingContract> contracts = savingContractRepository.findDepositContractForToday();
        this.delegate = new ListItemReader<>(contracts);
    }

    @Override
    public SavingContract read() {
        return delegate.read();
    }
}
