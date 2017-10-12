package com.fuwo.b3d.user.utils.password;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class DjangoPasswordEncoder implements PasswordEncoder {

    private static final Map<String, PasswordEncoder> ENCODERS = new HashMap<String, PasswordEncoder>();
    private static final String DEFAULT_ALGORITHM = "pbkdf2_sha256";

    static {
        ENCODERS.put("pbkdf2_sha256", new DjangoPbkdf2Sha256PasswordEncoder());
        ENCODERS.put("sha1", new DjangoSha1PasswordEncoder());
    }

    @Override
    public String encode(CharSequence rawPassword) {
        PasswordEncoder encoder = ENCODERS.get(DEFAULT_ALGORITHM);
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder encoder = null;
        for (String key : ENCODERS.keySet()) {
            if (encodedPassword.startsWith(key)) {
                encoder = ENCODERS.get(key);
                break;
            }
        }

        if (encoder == null) {
            throw new RuntimeException("Unsupported password hash algorithm");
        }

        return encoder.matches(rawPassword, encodedPassword);
    }
}
