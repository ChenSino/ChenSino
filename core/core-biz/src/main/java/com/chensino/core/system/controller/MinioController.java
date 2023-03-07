package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.oss.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("minio")
@AllArgsConstructor
public class MinioController {

    private final FileService fileService;

    @GetMapping("upload")
    public ResponseEntity uploadFile(@RequestParam String fileName){
        fileService.upload(fileName);
        return ResponseEntity.ok();
    }

}
