package com.e201.kidswallet.mission.transactional;

import com.e201.kidswallet.account.dto.TransferMoneyDTO;
import com.e201.kidswallet.account.service.AccountService;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.mission.dto.MissionCompleteCheckRequestDto;
import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.mission.repository.MissionRepository;
import com.e201.kidswallet.mission.service.MissionService;
import com.e201.kidswallet.transaction.repository.TransactionRepository;
import com.e201.kidswallet.user.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final MissionService missionService;
    private final AccountService accountService;
    private final MissionRepository missionRepository;

    @Autowired
    public TransactionService(MissionService missionService, AccountService transferMoney, AccountService accountService, MissionRepository missionRepository) {
        this.missionService = missionService;
        this.accountService = accountService;
        this.missionRepository = missionRepository;
    }

    @Transactional
    public StatusCode transferMissionservice(MissionCompleteCheckRequestDto requestDto){

        Beg findBeg = missionRepository.findById(requestDto.getMissionId()).get().getBeg();
        int money = findBeg.getBegMoney();
        long toUserId = findBeg.getRelation().getChild().getUserId();
        long fromUserId = findBeg.getRelation().getParent().getUserId();
        String fromUserName = findBeg.getRelation().getParent().getUserRealName();

        StatusCode missionCompleteCheckResult= missionService.missionCompleteCheck(requestDto);
        StatusCode transferResult = accountService.transferMoney(new TransferMoneyDTO(Long.toString(fromUserId),Long.toString(toUserId),fromUserName+"(MIssion)",money));

        if(missionCompleteCheckResult != StatusCode.SUCCESS){
            return missionCompleteCheckResult;
        }

        if(transferResult != StatusCode.SUCCESS){
            return transferResult;
        }

        return StatusCode.SUCCESS;
    }
}
