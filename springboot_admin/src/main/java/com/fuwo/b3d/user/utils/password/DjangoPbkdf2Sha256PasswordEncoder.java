package com.fuwo.b3d.user.utils.password;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DjangoPbkdf2Sha256PasswordEncoder implements PasswordEncoder {

    private final int DEFAULT_ITERATIONS = 12000;
    private final String algorithm = "pbkdf2_sha256";

    @Override
    public String encode(CharSequence password) {
        String result = null;
        try {
            return this.encode(password, DEFAULT_ITERATIONS, PasswordHashSaltUtils.genRandomSalt(12));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String encode(CharSequence password, int iterations, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String hash = getEncodedHash(password, iterations, salt);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    public String getEncodedHash(CharSequence password, int iterations, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }

        KeySpec keySpec = new PBEKeySpec(password.toString().toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            throw e;
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 4) {
            return false;
        }

        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = null;
        try {
            hash = encode(rawPassword, iterations, salt);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash != null && hash.equals(encodedPassword);
    }

}