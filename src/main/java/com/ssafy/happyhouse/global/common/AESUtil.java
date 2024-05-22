package com.ssafy.happyhouse.global.common;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Arrays;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

@Component
public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final String SEED = "NEWJIPS";

    public static SecretKey getKeyFromSeed() throws Exception {
        byte[] key = SEED.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // 128비트 AES 키를 위해 16바이트 사용
        return new SecretKeySpec(key, ALGORITHM);
    }

    // Encryption
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.encodeBase64String(encryptedBytes);
    }

    // Decryption
    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encryptedText));
        return new String(decryptedBytes);
    }

}
