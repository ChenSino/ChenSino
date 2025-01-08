package com.chensino.core.system.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.common.security.component.annotation.OpenApi;
import com.chensino.core.system.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author chenkun
 */
@RestController
@RequestMapping("minio")
@AllArgsConstructor
@Tag(name = "上传文件", description = "上传文件")
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

    @GetMapping("/files/{bucketName}")
    @OpenApi
    @SysLog("查询文件列表")
    @Operation(summary = "查询某个桶文件列表", description = "查询某个桶文件列表")
    @Parameter(name = "bucketName", description = "桶名", required = true)
    public ResponseEntity<ObjectListing> listFilesByBucket(@PathVariable(value = "bucketName") String bucketName) {
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
