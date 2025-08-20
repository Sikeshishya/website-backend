package com.filcon.electronics.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{10,15}$");

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }

    public String sanitizeInput(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("[<>\"'&]", "");
    }
}