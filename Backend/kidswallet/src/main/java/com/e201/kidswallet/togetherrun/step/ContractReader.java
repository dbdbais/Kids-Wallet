//package com.e201.kidswallet.togetherrun.step;
//
//import com.e201.kidswallet.togetherrun.entity.SavingContract;
//import com.e201.kidswallet.togetherrun.repository.SavingContractRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//public class ContractReader implements ItemReader<SavingContract> {
//
//    private final SavingContractRepository savingContractRepository;
//    private ListItemReader<SavingContract> delegate;
//    private List<SavingContract> currentChunk;
//    private int currentPage = 0;  // 현재 페이지를 추적하는 변수
//    private final int chunkSize = 1;  // chunk 크기
//
//    public ContractReader(SavingContractRepository savingContractRepository) {
//        this.savingContractRepository = savingContractRepository;
//    }
//
////    @PostConstruct
////    public void init() {
////        List<SavingContract> contracts = savingContractRepository.findDepositContractForToday();
////        System.out.println("Reader init");
////        this.delegate = new ListItemReader<>(contracts);
////        System.out.println("Read Test");
////        System.out.println("Read Size: " + contracts.size());
////        for (SavingContract sc : contracts) {
////            System.out.println(sc.getSavingContractId());
////        }
////    }
//
//    @Override
//    public SavingContract read() {
//        System.out.println("Reader read");
////        return delegate.read();
//        if (currentChunk == null || currentChunk.isEmpty()) {
//            Pageable pageable = PageRequest.of(currentPage, chunkSize);
//            currentChunk = savingContractRepository.findDepositContractForToday(pageable);
//            currentPage++;
//        }
//
//        if (!currentChunk.isEmpty()) {
//            return currentChunk.remove(0);
//        } else {
//            return null;  // 모든 데이터를 읽은 경우 null 반환
//        }
//    }
//}
