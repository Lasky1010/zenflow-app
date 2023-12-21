package edu.tms.zenflow.controller;

import edu.tms.zenflow.data.dto.comment.CommentDto;
import edu.tms.zenflow.service.CommentService;
import edu.tms.zenflow.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;
    private final ResponseErrorValidation responseErrorValidation;


    @PostMapping("/{postId}")
    public ResponseEntity<Object> createComment(@PathVariable Long postId,
                                                @Valid @RequestBody CommentDto commentDto,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        CommentDto created = commentService.saveComment(postId, commentDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long postId) {
        List<CommentDto> allComments = commentService.getAllCommentsForPost(postId);
        return ResponseEntity.ok(allComments);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDto> deletePost(@PathVariable Long commentId, Principal principal) {
        CommentDto commentDto = commentService.deleteComment(commentId, principal);
        return ResponseEntity.ok().body(commentDto);
    }


}
