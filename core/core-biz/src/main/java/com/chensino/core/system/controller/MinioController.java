package com.chensino.core.system.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.chensino.common.core.util.ResponseEntity;
import com.chensino.core.system.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "上传文件",tags = {"文件上传接口"})
public class MinioController {

    private final MinioService minioService;

    @PostMapping("upload")
    @ApiOperation(value = "多文件上传")
    public ResponseEntity<Void> uploadFile(@RequestParam("files") List<MultipartFile> files, @RequestParam String bucket) {
        minioService.upload(bucket, files);
        return ResponseEntity.ok();
    }

    /**
     * 查询桶列表
     *
     * @return
     */
    @GetMapping("buckets")
    public ResponseEntity<List<Bucket>> listBuckets() {
        return ResponseEntity.ok(minioService.getAllBuckets());
    }

    @GetMapping("/files/{bucketName}")
    public ResponseEntity<ObjectListing> listFilesByBucket(@PathVariable String bucketName) {
        return ResponseEntity.ok(minioService.listObjects(bucketName));
    }
}
