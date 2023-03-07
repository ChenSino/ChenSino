package com.chensino.common.oss.service;

import com.chensino.common.oss.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@AllArgsConstructor
public class MinioHelper {

    /**
     * 文件接口服务
     */

    private final FileService fileService;


    /**
     * 上传
     *
     * @param filename
     */
    public void upload(String filename) {
        Assert.notNull(filename, "filename is null.");
        fileService.upload(filename);
    }

    /**
     * 上传
     *
     * @param filename
     * @param object
     */
    public void upload(String filename, String object) {
        Assert.notNull(filename, "filename is null.");
        Assert.notNull(object, "object is null.");
        fileService.upload(filename, object);
    }

    /**
     * 上传
     *
     * @param filename
     * @param object
     * @param bucket
     */
    public void upload(String filename, String object, String bucket) {
        Assert.notNull(filename, "filename is null.");
        Assert.notNull(object, "object is null.");
        Assert.notNull(bucket, "bucket is null.");
        fileService.upload(filename, object, bucket);
    }

}
