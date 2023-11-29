package edu.tms.zenflow.controller;

import edu.tms.zenflow.data.dto.post.PostDto;
import edu.tms.zenflow.service.PostService;
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
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final ResponseErrorValidation responseErrorValidation;


    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.getErrors(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        PostDto created = postService.createPost(postDto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }


    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDto>> getAllUsersPosts(Principal principal) {
        List<PostDto> allPosts = postService.getPostsForUser(principal);
        return ResponseEntity.ok(allPosts);
    }


    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<Object> likePost(Long postId, String username) {
        PostDto likePost = postService.likePost(postId, username);
        return ResponseEntity.ok(likePost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(Long postId, Principal principal) {
        postService.deletePost(postId, principal);
        return ResponseEntity.ok("Post was deleted");
    }


}
