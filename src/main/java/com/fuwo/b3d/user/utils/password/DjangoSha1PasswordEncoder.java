package com.fuwo.b3d.user.utils.password;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DjangoSha1PasswordEncoder implements PasswordEncoder {

    private final String ALGORITHM_PREFIX = "sha1";
    private final String ALGORITHM = "SHA-1";

    @Override
    public String encode(CharSequence rawPassword) {
        return encode(rawPassword, PasswordHashSaltUtils.genRandomSalt(5));
    }

    public String encode(CharSequence rawPassword, String salt) {
        MessageDigest messageDigest = null;//MD5,MD2
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String password = salt + rawPassword.toString();
        String hash = fromBytesToHex(messageDigest.digest(password.getBytes(Charset.forName("UTF-8"))));

        return String.format("%s$%s$%s", ALGORITHM_PREFIX, salt, hash);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 3) {
            return false;
        }

        String salt = parts[1];
        String hash = encode(rawPassword, salt);

        return hash != null && hash.equals(encodedPassword);
    }


    private static String fromBytesToHex(byte[] resultBytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                        Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }
}
