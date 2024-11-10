package com.e201.kidswallet.user.controller;

import com.e201.kidswallet.account.dto.AccountInfoResponseDTO;
import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

//    @GetMapping("/info/{userId}")
//    public ResponseEntity<ResponseDto> getUserInfo(@PathVariable Long userId) {
//
//    }
}