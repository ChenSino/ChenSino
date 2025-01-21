/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.chensino.common.oss.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * aws-s3 通用存储操作 支持所有兼容s3协议的云存储: {阿里云OSS，腾讯云COS，七牛云，京东云，minio 等}
 *
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OssTemplate {


    private final S3Client amazonS3;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public  void createBucket( String bucketName) {
        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            amazonS3.createBucket(createBucketRequest);
        } catch (S3Exception e) {
            log.error("创建bucket失败", e);
        }
    }

    /**
     * 获取全部bucket
     * <p>
     *
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/ListBuckets">AWS
     * API Documentation</a>
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        ListBucketsResponse listBucketsResponse = amazonS3.listBuckets();
        return listBucketsResponse.buckets();
    }

    /**
     * @param bucketName bucket名称
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/ListBuckets">AWS
     * API Documentation</a>
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return amazonS3.listBuckets().buckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * @param bucketName bucket名称
     * @see <a href=
     * "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/DeleteBucket">AWS API
     * Documentation</a>
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        amazonS3.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName)
                .build());
    }

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @return S3Object 列表
     */
    @SneakyThrows
    public List<S3Object> getAllObjectsByPrefix(String bucketName, String prefix) {
        ListObjectsResponse objectListing = amazonS3.listObjects(ListObjectsRequest.builder().bucket(bucketName).prefix(prefix)
                .build());
       return objectListing.contents();
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     * @see S3Client#generatePresignedUrl(String bucketName, String key, Date expiration)
     */
//    @SneakyThrows
//    public String getObjectURL(String bucketName, String objectName, Integer expires) {
//        Date date = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, expires);
//        URL url = amazonS3.generatePresignedUrl(bucketName, objectName, calendar.getTime());
//        return url.toString();
//    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/GetObject">AWS
     * API Documentation</a>
     */
//    @SneakyThrows
//    public S3Object getObject(String bucketName, String objectName) {
//        return amazonS3.getObject(GetObjectRequest.builder().bucket(bucketName).key(objectName)
//                .build());
//    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param contextType 文件类型
     * @throws Exception
     */
    public void putObject(String bucketName, String objectName, InputStream stream, String contextType)
            throws Exception {
        putObject(bucketName, objectName, stream, stream.available(), contextType);
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/PutObject">AWS
     * API Documentation</a>
     */
//    public PutObjectResponse putObject(String bucketName, String objectName, InputStream stream, long size,
//                                     String contextType) throws Exception {
//        byte[] bytes = IOUtils.toByteArray(stream);
//        HeadObjectResponse objectMetadata = HeadObjectResponse.builder()
//                .build();
//        objectMetadata.contentLength(size);
//        objectMetadata.contentType(contextType);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        // 上传
//        return amazonS3.putObject(bucketName, objectName, byteArrayInputStream, objectMetadata);
//
//    }
    /**
     * 上传文件到指定的 S3 存储桶。
     *
     * @param bucketName   存储桶名称
     * @param objectName   对象名称（键）
     * @param stream       文件内容的输入流
     * @param size         文件大小（字节）
     * @param contentType  文件的内容类型（MIME 类型）
     * @return PutObjectResponse 上传操作的响应
     * @throws Exception 如果发生错误
     */
    public PutObjectResponse putObject(String bucketName, String objectName, InputStream stream, long size,
                                       String contentType) throws Exception {
        try {
            // 将输入流转换为字节数组
            byte[] bytes = stream.readAllBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            // 构建 PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .contentType(contentType)
                    .contentLength(size)
                    .build();

            // 使用 RequestBody.fromInputStream 提供文件内容
            RequestBody requestBody = RequestBody.fromInputStream(byteArrayInputStream, size);

            // 执行上传操作并返回响应
            return amazonS3.putObject(putObjectRequest, requestBody);
        } catch (S3Exception e) {
            // 捕获并处理 S3 相关异常
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }


    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/GetObject">AWS
     * API Documentation</a>
     */
//    public S3Object getObjectInfo(String bucketName, String objectName) throws Exception {
//        @CleanupResponseInputStream<GetObjectResponse> object = amazonS3.getObject(GetObjectRequest.builder().bucket(bucketName).key(objectName)
//                .build());
//        return object;
//    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception
     * @see <a href=
     * "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/DeleteObject">AWS API
     * Documentation</a>
     */
    public void removeObject(String bucketName, String objectName) {
        amazonS3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(objectName)
                .build());
    }

    /**
     * 查看某个桶下的文件
     *
     * @param bucketName
     * @return
     */
    public ListObjectsResponse listObjects(String bucketName) {
        return amazonS3.listObjects(ListObjectsRequest.builder().bucket(bucketName)
                .build());
    }
}
