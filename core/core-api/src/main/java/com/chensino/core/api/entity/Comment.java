package com.chensino.core.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenkun
 * @description: 帖子评论
 * @date: 2025-01-02 10:48:13
 **/
@TableName(value ="t_comment")
@Data
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    //帖子id
    private Long postId;
    //评论内容
    private String content;
    //图片地址
    private String picUrl;
    //父评论id，可能为空，比如一级评论直接回复的是帖子
    private Long parentId;
    //被回复的用户
    private String targetUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private String createBy;

}
