package com.fuwo.b3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Jerry on 2017/5/26.
 */
@SpringBootApplication
public class Application {

    public static final String RELEASE = "v1.0.0_M1";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
