package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.comment.CommentDto;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CommentDto saveComment(Long postId, CommentDto commentDto, Principal principal);

    List<CommentDto> getAllCommentsForPost(Long postId);

    void deleteComment(Long commentId, Principal principal);
}
