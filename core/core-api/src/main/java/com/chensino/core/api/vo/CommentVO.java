package com.chensino.core.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chensino.core.api.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 响应给前端的数据
 * @Date 2023/4/28 上午11:00
 * @Created by chenxk
 */
@Data
public class CommentVO   {
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
    //评论人
    private String createBy;
    //头像
    private String avatar;
    //点赞数
    private Integer likeCount;
    //二级评论
    private List<Comment> children;
}
