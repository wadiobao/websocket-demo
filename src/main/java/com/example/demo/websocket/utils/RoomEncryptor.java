package com.example.demo.websocket.utils;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class RoomEncryptor {
	
    public String encrypt(String plainText,String secretKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(Base64.getUrlDecoder().decode(secretKey), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getUrlEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText,String secretKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(Base64.getUrlDecoder().decode(secretKey), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getUrlDecoder().decode(cipherText);
        return new String(cipher.doFinal(decoded));
    }
    
    public String generateRandomKey16Bytes() {
        byte[] key = new byte[16]; // 16 bytes = 128 bits
        new SecureRandom().nextBytes(key);
        return Base64.getUrlEncoder().encodeToString(key); // Mã hóa Base64 để dễ lưu 2trữ
    }
    
}
