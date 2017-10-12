package com.fuwo.b3d.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss")
public class OSSProperties {

    private String bucketEndpoint;

    private String domain;

    private final Bucket image = new Bucket();

    public String getBucketEndpoint() {
        return bucketEndpoint;
    }

    public void setBucketEndpoint(String bucketEndpoint) {
        this.bucketEndpoint = bucketEndpoint;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Bucket getImage() {
        return image;
    }

    public static class Bucket {
        private String bucketName;
        private String bucketAccessKey;
        private String bucketAccessSecret;

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getBucketAccessKey() {
            return bucketAccessKey;
        }

        public void setBucketAccessKey(String bucketAccessKey) {
            this.bucketAccessKey = bucketAccessKey;
        }

        public String getBucketAccessSecret() {
            return bucketAccessSecret;
        }

        public void setBucketAccessSecret(String bucketAccessSecret) {
            this.bucketAccessSecret = bucketAccessSecret;
        }
    }


}
