package com.iotech.qualitrack.platform.iam.infrastructure.hashing.bcrypt.services;

import com.iotech.qualitrack.platform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCrypt implementation for password hashing operations.
 */
@Service
public class HashingServiceImpl implements HashingService {

    private final BCryptPasswordEncoder passwordEncoder;

    public HashingServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}