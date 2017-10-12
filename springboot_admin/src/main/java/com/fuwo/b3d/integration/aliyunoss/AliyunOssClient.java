package com.fuwo.b3d.integration.aliyunoss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.fuwo.b3d.GlobalConstant;
import com.fuwo.b3d.config.OSSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/18.
 */
@Service
public class AliyunOssClient {

    private final Logger logger = LoggerFactory.getLogger(AliyunOssClient.class);

    private final String SEPARATOR = "/";

    @Autowired
    private OSSProperties ossProperties;

    public String uploadByStream(String bucketName, String filePath, InputStream inputStream) {

        OSSClient ossClient = null;
        String key = "";
        try {
            // 创建OSSClient实例
            ossClient = new OSSClient(ossProperties.getBucketEndpoint(), ossProperties.getImage().getBucketAccessKey(), ossProperties.getImage().getBucketAccessSecret());
            // 上传文件流
            key = filePath;
            PutObjectResult result = ossClient.putObject(bucketName, key, inputStream);

        } catch (OSSException oe) {
            logger.error(("OSSException Error Message: " + oe.getMessage()));
            return null;
        } catch (ClientException ce) {
            logger.error("ClientException Error Message: " + ce.getMessage());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return ossProperties.getDomain() + SEPARATOR + key;

    }
}
