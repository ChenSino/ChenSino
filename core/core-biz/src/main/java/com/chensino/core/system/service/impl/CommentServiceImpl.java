package com.chensino.core.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chensino.core.api.entity.Comment;
import com.chensino.core.api.vo.CommentVO;
import com.chensino.core.system.mapper.CommentMapper;
import com.chensino.core.system.service.ICommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chenkun
 * @description:
 * @date: 2025-01-02 11:22:44
 **/
@EqualsAndHashCode(callSuper = true)
@Service
@Data
@AllArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    private final CommentMapper commentMapper;

    @Override
    public List<CommentVO> list(Long postId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getPostId, postId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        CollUtil.newArrayList();
        List<CommentVO> commentVOS = List.of();
        if (CollUtil.isNotEmpty(comments)) {
            List<Comment> firstLevelComments = comments.stream().filter(comment -> comment.getParentId() == null).toList();
            commentVOS  = BeanUtil.copyToList(firstLevelComments, CommentVO.class);
            commentVOS.forEach(commentVO -> {
                if (commentVO.getParentId() == null) {
                    List<Comment> childComments = comments.stream().filter(c -> Objects.equals(c.getParentId(), commentVO.getId())).toList();
                    commentVO.setChildren(childComments);
                }
            });
        }
        return commentVOS;
    }
}
