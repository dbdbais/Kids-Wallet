package com.e201.kidswallet.user.service;

import com.e201.kidswallet.account.dto.AccountInfoResponseDTO;
import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.dto.RegisterRequestDTO;
import com.e201.kidswallet.user.dto.RelationRequestDTO;
import com.e201.kidswallet.user.dto.UserLoginDTO;
import com.e201.kidswallet.user.dto.UserLoginResponseDTO;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public UserLoginResponseDTO loginUser(UserLoginDTO userLoginDTO){
        User sUser = getUserByName(userLoginDTO.getUserName());

        if(sUser == null){
            return new UserLoginResponseDTO(StatusCode.NO_USER);
        }
        else{
            if(isPasswordCorrect(sUser, userLoginDTO.getUserPassword())){
                Map<String, Long> responseMap = new HashMap<>();
                responseMap.put("userId", sUser.getUserId());
                return new UserLoginResponseDTO(StatusCode.SUCCESS,responseMap);
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

            //해당하는 Relation 추가
            Relation userRelation = Relation.builder()
                    .parent(pUser)
                    .child(cUser)
                    .build();

            cUser.getParentsRelations().add(userRelation);
            pUser.getChildrenRelations().add(userRelation);

            return StatusCode.SUCCESS;
        }

    }

}
