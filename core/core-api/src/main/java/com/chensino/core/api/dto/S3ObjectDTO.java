package com.chensino.core.api.dto;

import lombok.Data;
import software.amazon.awssdk.services.s3.model.S3Object;

@Data
public class S3ObjectDTO {
    private String key;
    private long size;


    public static S3ObjectDTO from(S3Object s3Object) {
        S3ObjectDTO dto = new S3ObjectDTO();
        dto.setKey(s3Object.key());
        dto.setSize(s3Object.size());
        return dto;
    }
}