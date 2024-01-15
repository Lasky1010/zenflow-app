package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.post.PostCreateDto;
import edu.tms.zenflow.data.dto.post.PostDto;

import java.security.Principal;
import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createPost(PostCreateDto post, Principal principal);

    List<PostDto> getPostsByUserId(Long userId);

    PostDto likePost(Long postId, String username);

    void deletePost(Long postId, Principal principal);

    List<PostDto> getPostsForUser(Principal principal);
}
