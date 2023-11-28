package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.post.PostDto;

import java.security.Principal;
import java.util.List;

public interface PostService {

    List<PostDto> findByUsername(String username);

    List<PostDto> getAllPosts();

    PostDto getPostByIdAndUser(Long postId, Principal principal);

    PostDto createPost(PostDto post, Principal principal);


    PostDto likePost(Long postId, String username);

    void deletePost(Long postId, Principal principal);

    List<PostDto> getPostsForUser(Principal principal);
}