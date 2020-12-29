package com.middleyun.minio;

import io.minio.MinioClient;

public class MinioClientUtil {

    private static MinioClient minioClient;

    static {
        minioClient = MinioClient.builder().endpoint("http://oss.lovestudy.world")
                .credentials("**", "**").build();
    }

    public static MinioClient getInstance() {
        return minioClient;
    }

}
