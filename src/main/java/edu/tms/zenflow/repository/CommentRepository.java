package edu.tms.zenflow.repository;

import edu.tms.zenflow.data.entity.Comment;
import edu.tms.zenflow.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPost(Post post);

    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);
}
