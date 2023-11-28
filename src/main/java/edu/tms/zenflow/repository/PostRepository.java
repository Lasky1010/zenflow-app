package edu.tms.zenflow.repository;

import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedAt(User user);
    List<Post> findAllByUserOrderByCreatedAtDesc(User user);

    List<Post> findAllByOrderByCreatedAt();

    Optional<Post> findByIdAndUser(Long id, User user);

}
