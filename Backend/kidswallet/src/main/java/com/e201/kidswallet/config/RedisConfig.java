package com.e201.kidswallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

    //@Value("${spring.redis.host}")
    private String host;

    //@Value("${spring.redis.port}")
    private String port;

//    @Value("${spring.redis.password}")
    private String password;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
//        //redisStandaloneConfiguration.setPassword(password);
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
//        return lettuceConnectionFactory;
//    }

}
