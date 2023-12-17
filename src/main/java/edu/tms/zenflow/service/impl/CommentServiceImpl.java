package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.dto.comment.CommentDto;
import edu.tms.zenflow.data.entity.Comment;
import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.CommentNotFoundException;
import edu.tms.zenflow.data.exception.PostNotFoundException;
import edu.tms.zenflow.data.mapper.CommentMapper;
import edu.tms.zenflow.repository.CommentRepository;
import edu.tms.zenflow.repository.PostRepository;
import edu.tms.zenflow.repository.UserRepository;
import edu.tms.zenflow.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static edu.tms.zenflow.data.constants.BadRequestConstants.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto, Principal principal) {
        User byPrincipal = findByPrincipal(principal);

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        Comment comment = commentMapper.mapTo(commentDto, post, byPrincipal);
        return commentMapper.mapTo(commentRepository.save(comment));

    }


    @Override
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        List<Comment> comment = commentRepository.findAllByPostOrderByCreatedAtDesc(post);

        return commentMapper.mapTo(comment);
    }

    @Override
    public void deleteComment(Long commentId, Principal principal) {
        User byPrincipal = findByPrincipal(principal);

        Comment comment = commentRepository.findByIdAndUserId(commentId, byPrincipal.getId()).orElseThrow(() -> new CommentNotFoundException(COMMENT_NOT_FOUND));

        commentRepository.delete(comment);

    }

    public User findByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
    }

}
