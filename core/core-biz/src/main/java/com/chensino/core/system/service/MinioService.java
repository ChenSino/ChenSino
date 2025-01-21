package com.chensino.core.system.service;

import com.chensino.core.api.dto.BucketDTO;
import com.chensino.core.api.dto.S3ObjectDTO;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Grant;

import java.util.List;

/**
 * @author chenkun
 * @Date 2023/5/6 下午2:37
 */
public interface MinioService {

    void upload(String bucketName, List<MultipartFile>  files);
    List<BucketDTO> getAllBuckets();

    List<S3ObjectDTO> listObjects(String bucketName);

    String testVisitPrivate();

    List<Grant> getBucketAcl(String bucketName);

}
