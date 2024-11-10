//package com.e201.kidswallet.togetherrun.step;
//
//import com.e201.kidswallet.togetherrun.dto.SavingTransferProcessorDto;
//import com.e201.kidswallet.togetherrun.repository.SavingPaymentRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//public class PaymentResultWriter implements ItemWriter<SavingTransferProcessorDto> {
//
//    private final SavingPaymentRepository savingPaymentRepository;
//
//    public PaymentResultWriter(SavingPaymentRepository savingPaymentRepository) {
//        this.savingPaymentRepository = savingPaymentRepository;
//    }
//
//    @Override
//    public void write(Chunk<? extends SavingTransferProcessorDto> chunk) throws Exception {
//        System.out.println("Writer");
//        List<? extends SavingTransferProcessorDto> items = chunk.getItems();
//        for (SavingTransferProcessorDto item : items) {
////            System.out.println("Batch Writer: " + item.getChildPayment().getDepositAmount());
////            System.out.println("Batch Writer: " + item.getChildPayment().getUser());
////            System.out.println("Batch Writer: " + item.getChildPayment().getDepositDate());
////            System.out.println("Batch Writer: " + item.getChildPayment().getSavingContract());
////            System.out.println("Batch Writer: " + item.getChildPayment());
////            System.out.println("Batch Writer: " + item.getParentsPayment());
//            try {
//                System.out.println("Saving Result: " + savingPaymentRepository.save(item.getChildPayment()));
//                System.out.println("Saving Result: " + savingPaymentRepository.save(item.getParentsPayment()));
//                System.out.println("Saved");
//            } catch (Exception e) {
//                System.out.println("Error" + e.getMessage());
//                e.printStackTrace();
//            }
//
//        }
//    }
//}
