package com.example.jwtapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);
    private final Map<String, String> otpStore = new HashMap<>();
    private final Random random = new Random();

    public String generateAndStoreOtp(String phoneNumber) {
        String otp = String.format("%06d", random.nextInt(1000000));
        otpStore.put(phoneNumber, otp);
        logger.info("Generated OTP for phone number {}: {}", phoneNumber, otp);
        return otp;
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        String storedOtp = otpStore.get(phoneNumber);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(phoneNumber);
            return true;
        }
        return false;
    }
}