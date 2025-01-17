package com.chensino.core.system.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author chenkun
 * @Date 2023/5/6 下午2:37
 */
public interface MinioService {

    void upload(String bucketName, List<MultipartFile>  files);
    List<Bucket> getAllBuckets();

    ObjectListing listObjects(String bucketName);

    String testVisitPrivate();

    List<Grant> getBucketAcl(String bucketName);

}
