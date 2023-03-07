package com.chensino.common.oss.service.impl;

import com.chensino.common.oss.service.FileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class FileServiceImpl implements FileService {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * minio 客户端
     */

    @Autowired
    private  MinioClient minioClient;

    /**
     * 默认存储桶名称
     */
    @Value("${minio.bucketName:chensino}")
    private  String defaultBucketName;


    @Override
    public void upload(String filename) {
        uploadObject(filename, null, defaultBucketName);
    }

    @Override
    public void upload(String filename, String object) {
        uploadObject(filename, object, defaultBucketName);
    }


    @Override
    public void upload(String filename, String object, String bucket) {
        uploadObject(filename, object, bucket);
    }

    /**
     * 上传
     *
     * @param filename
     * @param object
     * @param bucket
     */
    private void uploadObject(String filename, String object, String bucket) {
        if (!StringUtils.hasText(filename) || !StringUtils.hasText(bucket))
            return;
        try {
            //存储桶构建
            bucketBuild(bucket);
            //保存的文件名称
            object = !StringUtils.hasText(object) ? filename.substring(filename.lastIndexOf("/") > 0 ? filename.lastIndexOf("/") : filename.lastIndexOf("\\")) : object;

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .filename(filename)
                            .build());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException exception) {
            LOG.error("uploadObject error", exception);
        }
    }


    /**
     * 存储桶构建
     *
     * @param bucketName
     */
    private void bucketBuild(String bucketName) {
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                LOG.info("Bucket " + bucketName + " make success.");
            } else {
                LOG.info("Bucket " + bucketName + " already exists.");
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException exception) {
            LOG.error("bucketBuild error", exception);
        }
    }

    public String getDefaultBucketName() {
        return defaultBucketName;
    }

    public void setDefaultBucketName(String defaultBucketName) {
        this.defaultBucketName = defaultBucketName;
    }

}
