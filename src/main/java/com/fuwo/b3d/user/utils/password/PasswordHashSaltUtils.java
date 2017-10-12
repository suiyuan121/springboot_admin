package com.fuwo.b3d.user.utils.password;

import java.util.Random;

public abstract class PasswordHashSaltUtils {

    public static String genRandomSalt(int len) {
        Random rand = new Random();
        char[] rs = new char[len];
        for (int i = 0; i < len; i++) {
            int t = rand.nextInt(3);
            if (t == 0) {
                rs[i] = (char) (rand.nextInt(10) + 48);
            } else if (t == 1) {
                rs[i] = (char) (rand.nextInt(26) + 65);
            } else {
                rs[i] = (char) (rand.nextInt(26) + 97);
            }
        }
        return new String(rs);
    }
}
