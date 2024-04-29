package com.example.corruptionperceptionindex.src.components;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPassword {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;

    public static String hashPassword(String password, String salt) {
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory;
        byte[] hash = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        byte[] combined = new byte[saltBytes.length + hash.length];
        System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
        System.arraycopy(hash, 0, combined, saltBytes.length, hash.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
//    public static String hashPassword(String password) {
//        // Salt should be unique for each user
//        byte[] salt = new byte[16];
//        // You may use a more secure random number generator
//        new SecureRandom().nextBytes(salt);
//
//        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory;
//        byte[] hash = null;
//        try {
//            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            hash = factory.generateSecret(spec).getEncoded();
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//
//        byte[] combined = new byte[salt.length + hash.length];
//        System.arraycopy(salt, 0, combined, 0, salt.length);
//        System.arraycopy(hash, 0, combined, salt.length, hash.length);
//
//        return Base64.getEncoder().encodeToString(combined);
//    }
}
