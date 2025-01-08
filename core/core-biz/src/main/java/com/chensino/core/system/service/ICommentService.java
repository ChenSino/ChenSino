package com.chensino.core.system.service;

import com.chensino.core.api.vo.CommentVO;

import java.util.List;

/**
 * @author chenkun
 * @description: 评论
 * @date: 2025-01-02 11:21:24
 **/
public interface ICommentService {

    /**
     * 获取帖子评论列表
     * @param postId 帖子id
     * @return 评论列表
     */
    List<CommentVO> list(Long postId);
}
