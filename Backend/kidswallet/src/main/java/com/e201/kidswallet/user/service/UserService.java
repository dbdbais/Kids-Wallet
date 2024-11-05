package com.e201.kidswallet.user.service;

import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.dto.RegisterRequestDTO;
import com.e201.kidswallet.user.dto.RelationRequestDTO;
import com.e201.kidswallet.user.dto.UserLoginDTO;
import com.e201.kidswallet.user.entity.Relation;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public StatusCode loginUser(UserLoginDTO userLoginDTO){
        User sUser = getUserByName(userLoginDTO.getUserName());

        if(sUser == null){
            return StatusCode.NO_USER;
        }
        else{
            if(isPasswordCorrect(sUser, userLoginDTO.getPassword())){
                return StatusCode.SUCCESS;
            }
            else{
                return StatusCode.WRONG_PW;
            }
        }
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
            pUser.getChildsRelations().add(userRelation);

            return StatusCode.SUCCESS;
        }

    }

}
