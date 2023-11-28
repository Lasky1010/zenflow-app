package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.dto.post.PostDto;
import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.PostNotFoundException;
import edu.tms.zenflow.data.mapper.PostMapper;
import edu.tms.zenflow.repository.PostRepository;
import edu.tms.zenflow.repository.UserRepository;
import edu.tms.zenflow.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static edu.tms.zenflow.data.constants.BadRequestConstants.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    @Override
    public List<PostDto> findByUsername(String username) {
        return null;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return mapper.mapTo(repository.findAllByOrderByCreatedAt());
    }

    @Override
    public PostDto getPostByIdAndUser(Long postId, Principal principal) {
        User byPrincipal = findByPrincipal(principal);
        return mapper.mapTo(repository.findByIdAndUser(postId, byPrincipal).
                orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND)));
    }

    @Override
    public PostDto createPost(PostDto post, Principal principal) {
        User byPrincipal = findByPrincipal(principal);
        Post fromDto = mapper.mapTo(post);
        fromDto.setUser(byPrincipal);
        byPrincipal.getPosts().add(fromDto);
        return mapper.mapTo(fromDto);

    }


    @Override
    public PostDto likePost(Long postId, String username) {
        Post post = repository.findById(postId).orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));

        Optional<String> first = post.getWhoLikes().stream().filter((us) -> us.equals(username)).findFirst();

        if (first.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getWhoLikes().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getWhoLikes().add(username);
        }
        return mapper.mapTo(repository.save(post));
    }

    @Override
    public void deletePost(Long postId, Principal principal) {

        User byPrincipal = findByPrincipal(principal);
        Post post = repository.findByIdAndUser(postId, byPrincipal).orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        repository.delete(post);
    }


    @Override
    public List<PostDto> getPostsForUser(Principal principal) {
        User byPrincipal = findByPrincipal(principal);
        return mapper.mapTo(repository.findAllByUserOrderByCreatedAtDesc(byPrincipal));
    }

    public User findByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found exception"));
    }
}
