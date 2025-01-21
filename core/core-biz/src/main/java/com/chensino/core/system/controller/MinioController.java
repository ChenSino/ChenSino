package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.common.security.component.annotation.OpenApi;
import com.chensino.core.system.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.util.List;

/**
 * @author chenkun
 */
@RestController
@RequestMapping("minio")
@AllArgsConstructor
@Tag(name = "Minio管理", description = "Minio管理")
public class MinioController {

    private final MinioService minioService;

    @PostMapping("upload")
    @Operation(summary = "多文件上传", description = "多文件上传")
    @Parameter(name = "files", description = "文件", required = true)
    public ResponseEntity<Void> uploadFile(@RequestParam("files") List<MultipartFile> files, @RequestParam String bucket) {
        minioService.upload(bucket, files);
        return ResponseEntity.ok();
    }

    @GetMapping("buckets")
    @Operation(summary = "查询桶列表", description = "查询桶列表")
    public ResponseEntity<List<Bucket>> listBuckets() {
        return ResponseEntity.ok(minioService.getAllBuckets());
    }

    @GetMapping("/bucket/{bucketName}/acl")
    @SysLog("查询bucket权限")
    @Operation(summary = "查询bucket权限", description = "查询bucket权限")
    @Parameter(name = "bucketName", description = "桶名", required = true,in = ParameterIn.PATH)
    public ResponseEntity<List<Grant>> getBucketAcl(@PathVariable(value = "bucketName") String bucketName) {
        return ResponseEntity.ok(minioService.getBucketAcl(bucketName));
    }

    @GetMapping("/files/{bucketName}")
    @SysLog("查询文件列表")
    @Operation(summary = "查询某个桶文件列表", description = "查询某个桶文件列表")
    @Parameter(name = "bucketName", description = "桶名", required = true)
    public ResponseEntity<ListObjectsResponse> listFilesByBucket(@PathVariable(value = "bucketName") String bucketName) {
        return ResponseEntity.ok(minioService.listObjects(bucketName));
    }

    @OpenApi
    @SysLog("测试访问私有文件")
    @Operation(summary = "测试访问私有文件", description = "测试访问私有文件")
    @GetMapping("/private/test")
    public ResponseEntity<String> testVisitPrivate() {
        return ResponseEntity.ok(minioService.testVisitPrivate());
    }
}
