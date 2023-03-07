package com.chensino.common.oss.service;

public interface FileService {

    /**
     * 上传
     *
     * @param filename 文件名
     */
    void upload(String filename);

    /**
     * 上传
     *
     * @param filename 文件名
     * @param object   保存对象文件名称
     */
    void upload(String filename, String object);

    /**
     * 上传
     *
     * @param filename 文件名称
     * @param object   保存对象文件名称
     * @param bucket   存储桶
     */
    void upload(String filename, String object, String bucket);

}
