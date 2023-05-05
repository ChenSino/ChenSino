package com.chensino.core.system.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.oss.service.OssTemplate;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.util.List;

/**
 * @author chenkun
 */
@RestController
@RequestMapping("minio")
@AllArgsConstructor
public class MinioController {

    private final OssTemplate ossTemplate;

    @GetMapping("upload")
    public ResponseEntity<Void> uploadFile(@RequestParam String file,@RequestParam String bucket,@RequestParam String objectName) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        ossTemplate.putObject(bucket,objectName,fileInputStream);
        return ResponseEntity.ok();
    }
    @GetMapping("buckets")
    public ResponseEntity<List<Bucket>> listBuckets() {
        return ResponseEntity.ok(ossTemplate.getAllBuckets());
    }

}
