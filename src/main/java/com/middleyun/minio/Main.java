package com.middleyun.minio;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws Exception {
        MinioClient minioClient = MinioClientUtil.getInstance();
        String url = minioClient.getObjectUrl("test", "5ea0fe210f06be4a167bf5e8684aa10a.png");
        System.out.println(url);

        // 上传
        minioClient.uploadObject(UploadObjectArgs.builder().bucket("test")
                .filename("G://hwpicture/6.jpg")
                .object("6.jpg").build());

        // 删除
        minioClient.removeObject(RemoveObjectArgs.builder().bucket("test")
                .object("5ea0fe210f06be4a167bf5e8684aa10a.png").build());
    }
}
