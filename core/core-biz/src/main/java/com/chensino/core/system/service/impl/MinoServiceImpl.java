package com.chensino.core.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrPool;
import com.chensino.common.core.exception.BadParameterException;
import com.chensino.common.core.exception.BusinessException;
import com.chensino.common.oss.service.OssTemplate;
import com.chensino.core.api.dto.BucketDTO;
import com.chensino.core.api.dto.S3ObjectDTO;
import com.chensino.core.system.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.Grant;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chenkun
 * @Date 2023/5/6 下午2:37
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MinoServiceImpl implements MinioService {

    private final OssTemplate ossTemplate;

    @Override
    public void upload(String bucketName, List<MultipartFile> files) {
        Optional.ofNullable(files).orElseThrow(BadParameterException::new);
        for (MultipartFile file : files) {
            try {
                String suffix = FileUtil.getSuffix(file.getOriginalFilename());
                String newFileName = UUID.randomUUID().toString().replace(StrPool.DASHED, "") + StrPool.DOT + suffix;
                ossTemplate.putObject(bucketName, newFileName, file.getInputStream());
                //TODO 写入数据库记录，记录源文件名，新文件名{生成一个不重复的名字}，存储url,创建人，时间，类型等
                //...
            } catch (Exception e) {
                throw new BusinessException("上传文件出现异常", e);
            }
        }
    }

    @Override
    public List<BucketDTO> getAllBuckets() {
        List<Bucket> buckets = ossTemplate.getAllBuckets();
        // 将 Bucket 列表转换为 BucketDTO 列表
        return BucketDTO.toDTOList(buckets);

    }

    @Override
    public List<S3ObjectDTO> listObjects(String bucketName) {
        return ossTemplate.listObjects(bucketName).contents().stream().map(S3ObjectDTO::from
        ).toList();
    }

    @Override
    public String testVisitPrivate() {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60);

        return "";
    }

    @Override
    public List<Grant> getBucketAcl(String bucketName) {

        return List.of();
    }
}
