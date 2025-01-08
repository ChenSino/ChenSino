package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenkun
 * @description: 帖子
 * @date: 2025-01-02 10:48:13
 **/
@TableName(value ="t_post")
@Data
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //内容
    private String content;
    //图片
    private List<String> picUrls;
    //帖子状态
    private  Integer status;
    //是否公开
    private Boolean isPublic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createBy;
    private String updateBy;

}
