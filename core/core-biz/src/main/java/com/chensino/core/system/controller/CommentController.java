package com.chensino.core.system.controller;

import com.chensino.common.core.util.ResponseEntity;
import com.chensino.common.data.configuration.cache.IGlobalCache;
import com.chensino.common.log.annotation.SysLog;
import com.chensino.core.api.entity.Comment;
import com.chensino.core.api.entity.SysUser;
import com.chensino.core.api.vo.CommentVO;
import com.chensino.core.system.service.ICommentService;
import com.chensino.core.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 204506
 * @version 1.0
 * @Date 2022-07-28 9:33
 */
@RestController
@RequestMapping("comment")
@AllArgsConstructor
@Tag(name = "评论管理", description = "评论管理")
public class CommentController {

    private final ICommentService commentService;

    @GetMapping("/list/{postId}")
    @Operation(summary = "查询评论列表", description = "查询评论列表")
    @Parameter(name = "postId", description = "帖子id", required = true)
    public ResponseEntity<List<CommentVO>> commentList(@PathVariable(name="postId") Long postId) {
        return ResponseEntity.ok(commentService.list(postId));
    }


}
