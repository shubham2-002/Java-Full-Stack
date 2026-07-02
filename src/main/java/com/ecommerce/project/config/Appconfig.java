package com.ecommerce.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration This class contains bean definitions that should be managed by the Spring IoC container."
//Think of it as a Java-based replacement for the old XML configuration files.
@Configuration


public class Appconfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
