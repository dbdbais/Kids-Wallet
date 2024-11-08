package com.e201.kidswallet.togetherrun.step;

import java.util.List;

import com.e201.kidswallet.togetherrun.dto.SavingTransferProcessorDto;
import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentResultWriter implements ItemWriter<SavingTransferProcessorDto> {

    private final SavingPaymentRepository savingPaymentRepository;

    public PaymentResultWriter(SavingPaymentRepository savingPaymentRepository) {
        this.savingPaymentRepository = savingPaymentRepository;
    }

    @Override
    public void write(Chunk<? extends SavingTransferProcessorDto> chunk) throws Exception {
        List<? extends SavingTransferProcessorDto> items = chunk.getItems();
        for (SavingTransferProcessorDto item : items) {
            savingPaymentRepository.save(item.getChildPayment());
            savingPaymentRepository.save(item.getParentsPayment());
        }
    }
}
