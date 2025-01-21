package com.chensino.core.system.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.util.List;

/**
 * @author chenkun
 * @Date 2023/5/6 下午2:37
 */
public interface MinioService {

    void upload(String bucketName, List<MultipartFile>  files);
    List<Bucket> getAllBuckets();

    ListObjectsResponse listObjects(String bucketName);

    String testVisitPrivate();

    List<Grant> getBucketAcl(String bucketName);

}
