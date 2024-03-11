package com.qino.config;

import com.github.javafaker.Faker;
import com.qino.util.UsernameValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public UsernameValidator userValidator() {
        return new UsernameValidator();
    }
}
