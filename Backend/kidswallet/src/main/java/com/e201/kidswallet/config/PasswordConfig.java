package com.e201.kidswallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class PasswordConfig {
    //Bcrypt 형식으로 암호화 해주는 Encoder 빈으로 등록
    @Bean
    public BCryptPasswordEncoder bCryptPassWordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
