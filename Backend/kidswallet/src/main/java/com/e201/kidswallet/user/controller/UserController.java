package com.e201.kidswallet.user.controller;

import com.e201.kidswallet.account.dto.AccountInfoResponseDTO;
import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.fcm.service.FcmService;
import com.e201.kidswallet.user.dto.RegisterRequestDTO;
import com.e201.kidswallet.user.dto.RelationRequestDTO;
import com.e201.kidswallet.user.dto.UserLoginDTO;
import com.e201.kidswallet.user.dto.UserLoginResponseDTO;
import com.e201.kidswallet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final FcmService fcmService;

    @Autowired
    public UserController(UserService userService, FcmService fcmService) {
        this.userService = userService;
        this.fcmService = fcmService;
    }

    /**
     * 유저 회원가입 함수.
     * @param registerRequestDTO
     * @return statusCode 반환
     */

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registUser( @RequestBody RegisterRequestDTO registerRequestDTO){
        StatusCode returnCode = userService.registerUser(registerRequestDTO);
        return ResponseDto.response(returnCode);
    }

    /**
     * 유저 로그인 함수.
     * @param userLoginDTO
     * @return statusCode 반환
     */
    
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody UserLoginDTO userLoginDTO){

        UserLoginResponseDTO userLoginResponseDTO = userService.loginUser(userLoginDTO);
        return ResponseDto.response(userLoginResponseDTO.getStatusCode(),userLoginResponseDTO);
    }

    /**
     * 부모관계 매핑 함수.
     * @param relationRequestDTO
     * @return
     */

    @PostMapping("/relation")
    public ResponseEntity<ResponseDto> addRelation(@RequestBody RelationRequestDTO relationRequestDTO) {


        StatusCode returnCode = userService.setRelation(relationRequestDTO);
        if(returnCode == StatusCode.SUCCESS){
            Long childId = userService.getUserByName(relationRequestDTO.getChildName()).getUserId();
            if(fcmService.sendMessage(fcmService.getToken(childId),"부모 등록 완료","부모가 등록되었어요.") == StatusCode.TOKEN_IS_NULL){
                returnCode = StatusCode.TOKEN_IS_NULL;
            }
        }

        return ResponseDto.response(returnCode);
    }

    /**
     * 유저가 가지는 계좌 정보를 모두 조회하는 로직
     * @param userId
     * @return 모든 계좌 정보 반환
     */

    @GetMapping("/accounts/{userId}")
    public ResponseEntity<ResponseDto> getAccounts(@PathVariable Long userId) {
        List<AccountInfoResponseDTO> lst = userService.getAccountInfo(userId);
        return ResponseDto.response(StatusCode.SUCCESS, lst);
    }

    /**
     * 유저가 카드를 발급하는 로직
     * @param userId
     * @return 카드발급 성공 여부 반환
     */

    @PatchMapping("/card/{userId}")
    public ResponseEntity<ResponseDto> getCard(@PathVariable Long userId) {
        StatusCode statusCode = userService.getCard(userId);
        return ResponseDto.response(statusCode);
    }

    /**
     * FCM에서 유저의 최신 정보를 갱신하기 위한 엔드포인트
     * @param userId
     * @return
     */

    @GetMapping("/status/{userId}")
    public ResponseEntity<ResponseDto> getStatus(@PathVariable Long userId){
        UserLoginResponseDTO userLoginResponseDTO = userService.getUserStatus(userId);
        return ResponseDto.response(userLoginResponseDTO.getStatusCode(),userLoginResponseDTO);
    }

//    @GetMapping("/info/{userId}")
//    public ResponseEntity<ResponseDto> getUserInfo(@PathVariable Long userId) {
//
//    }
}