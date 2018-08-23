package com.example.demo.config;

import com.example.demo.services.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.persistence.EntityManagerFactory;
import java.net.Authenticator;

@Configuration
public class BeanConfiguration {





    @Bean
    public String getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            if(!(authentication instanceof AnonymousAuthenticationToken)){
                return authentication.getName();
            }
        }
        return null;
    }
}
