package com.chensino.core.api.dto;

import lombok.Data;
import software.amazon.awssdk.services.s3.model.Bucket;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BucketDTO {

    private String name;
    private String creationDate;

    // 全参构造函数
    public BucketDTO(String name, String creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    // 静态方法：将 List<Bucket> 转换为 List<BucketDTO>
    public static List<BucketDTO> toDTOList(List<Bucket> buckets) {
        return buckets.stream()
                .map(bucket -> new BucketDTO(
                        bucket.name(),
                        bucket.creationDate() != null ? bucket.creationDate().toString() : null))
                .collect(Collectors.toList());
    }

    // 重写 toString 方法方便调试
    @Override
    public String toString() {
        return "BucketDTO{" +
                "name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}