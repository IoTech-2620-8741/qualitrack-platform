package com.iotech.qualitrack.platform.iam.infrastructure.hashing.bcrypt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt hashing configuration.
 */
@Configuration
public class BCryptHashingService {

    /**
     * Creates the BCrypt password encoder bean.
     *
     * @return BCrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}