package com.e201.kidswallet.user.service;

import com.e201.kidswallet.account.dto.AccountInfoResponseDTO;
import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.dto.*;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.enums.Role;
import com.e201.kidswallet.user.repository.RelationRepository;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RelationRepository relationRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RelationRepository relationRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.relationRepository = relationRepository;
    }

    private User getUserById (long userId){
        return userRepository.findById(userId).orElse(null);
    }
    private User getUserByName(String userName){
        return userRepository.findByUserName(userName).orElse(null);
    }

    private boolean isPasswordCorrect(User user,String password){
        return bCryptPasswordEncoder.matches(password, user.getUserPassword());
    }

    public StatusCode registerUser(RegisterRequestDTO registerRequestDTO) {
        User rUser = User.builder()
                .userName(registerRequestDTO.getUserName())
                .userPassword(bCryptPasswordEncoder.encode(registerRequestDTO.getUserPassword()))
                .userBirth(registerRequestDTO.getUserBirth())
                .userGender(registerRequestDTO.getUserGender())
                .userRealName(registerRequestDTO.getUserRealName())
                .userRole(registerRequestDTO.getUserRole())
                .build();

        userRepository.save(rUser);
        //User 실제로 넣는다.

        return StatusCode.SUCCESS;
    }

    public StatusCode getCard(Long userId){
        Optional<User> sUser = userRepository.findById(userId);

        if(sUser.isEmpty()){
            return null;
        }
        if(sUser.get().isHasCard()){
            return StatusCode.ALREADY_HAS_CARD;
        }
        else{
            sUser.get().makeCard();
            return StatusCode.SUCCESS;
        }
    }

    public UserLoginResponseDTO loginUser(UserLoginDTO userLoginDTO){
        User sUser = getUserByName(userLoginDTO.getUserName());

        if(sUser == null){
            //user가 없다면
            return new UserLoginResponseDTO(StatusCode.NO_USER);
        }
        else{
            if(isPasswordCorrect(sUser, userLoginDTO.getUserPassword())){
                //비밀번호가 맞다면
                List<ParentChildResponseDTO> rLst = new ArrayList<>();

                List<Relation> lst = relationRepository.findRelation(sUser.getUserId());


                if(sUser.getUserRole() == Role.PARENT){
                    for(Relation r : lst){
                        rLst.add(ParentChildResponseDTO.builder()
                                .userId(r.getChild().getUserId())
                                .userName(r.getChild().getUserName())
                                .userGender(r.getChild().getUserGender())
                                .build());
                    }
                }
                else if (sUser.getUserRole() == Role.CHILD){
                    for(Relation r : lst){
                        rLst.add(ParentChildResponseDTO.builder()
                                .userId(r.getParent().getUserId())
                                .userName(r.getParent().getUserName())
                                .userGender(r.getParent().getUserGender())
                                .build());
                    }
                }

                return
                        UserLoginResponseDTO.builder()
                                .statusCode(StatusCode.SUCCESS)
                                .userId(sUser.getUserId())
                                .userMoney(sUser.getUserMoney())
                                .userName(sUser.getUserName())
                                .userGender(sUser.getUserGender())
                                .userRealName(sUser.getUserRealName())
                                .userBirth(sUser.getUserBirth())
                                .representAccountId(sUser.getRepresentAccountId())
                                .userRole(sUser.getUserRole())
                                .hasCard(sUser.isHasCard())
                                .relations(rLst)
                        .build();
            }
            else{
                return new UserLoginResponseDTO(StatusCode.WRONG_PW);
            }
        }
    }

    public List<AccountInfoResponseDTO> getAccountInfo(Long id){
        Optional<User> sUser = userRepository.findById(id);

        if(sUser.isEmpty()){
            return null;
        }

        return sUser.get().getAccounts().stream()
                .map(a -> AccountInfoResponseDTO.builder()
                                .accountId(a.getAccountId())
                                .balance(a.getBalance())
                                .createdAt(a.getCreatedAt())
                                .build()).toList();
    }

    public StatusCode setRelation(RelationRequestDTO relationRequestDTO){
        User cUser = getUserByName(relationRequestDTO.getChildName());
        User pUser = getUserByName(relationRequestDTO.getParentName());


        if(cUser == null || pUser == null ){
            return StatusCode.NO_USER;
        }
        else{
            // 관계 추가
            Relation userRelation = Relation.builder()
                    .parent(pUser)
                    .child(cUser)
                    .build();

            relationRepository.save(userRelation);

            return StatusCode.SUCCESS;
        }

    }

    public UserLoginResponseDTO getUserStatus(Long userId){
        Optional<User> sUser = userRepository.findById(userId);

        if(sUser.isEmpty()){
            return null;
        }
        List<ParentChildResponseDTO> rLst = new ArrayList<>();

        List<Relation> lst = relationRepository.findRelation(sUser.get().getUserId());


        if(sUser.get().getUserRole() == Role.PARENT){
            for(Relation r : lst){
                rLst.add(ParentChildResponseDTO.builder()
                        .userId(r.getChild().getUserId())
                        .userName(r.getChild().getUserName())
                        .userGender(r.getChild().getUserGender())
                        .build());
            }
        }
        else if (sUser.get().getUserRole() == Role.CHILD){
            for(Relation r : lst){
                rLst.add(ParentChildResponseDTO.builder()
                        .userId(r.getParent().getUserId())
                        .userName(r.getParent().getUserName())
                        .userGender(r.getParent().getUserGender())
                        .build());
            }
        }

        return
                UserLoginResponseDTO.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .userId(sUser.get().getUserId())
                        .userMoney(sUser.get().getUserMoney())
                        .userName(sUser.get().getUserName())
                        .userGender(sUser.get().getUserGender())
                        .userRealName(sUser.get().getUserRealName())
                        .userBirth(sUser.get().getUserBirth())
                        .representAccountId(sUser.get().getRepresentAccountId())
                        .userRole(sUser.get().getUserRole())
                        .hasCard(sUser.get().isHasCard())
                        .relations(rLst)
                        .build();

    }


}
