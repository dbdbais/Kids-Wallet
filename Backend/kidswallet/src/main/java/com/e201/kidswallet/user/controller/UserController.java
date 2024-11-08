package com.e201.kidswallet.user.controller;

import com.e201.kidswallet.common.ResponseDto;
import com.e201.kidswallet.common.exception.StatusCode;
import com.e201.kidswallet.user.dto.RegisterRequestDTO;
import com.e201.kidswallet.user.dto.RelationRequestDTO;
import com.e201.kidswallet.user.dto.UserLoginDTO;
import com.e201.kidswallet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        StatusCode returnCode = userService.loginUser(userLoginDTO);
        return ResponseDto.response(returnCode);
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

}
