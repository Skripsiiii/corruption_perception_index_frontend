package com.example.corruptionperceptionindex.src.components;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidateEmailPassword {
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        return Pattern.matches(passwordPattern, password);
    }
}
