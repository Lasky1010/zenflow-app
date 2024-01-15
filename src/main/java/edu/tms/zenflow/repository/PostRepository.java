package edu.tms.zenflow.repository;

import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByCreatedAtDesc(User user);

    List<Post> findAllByOrderByCreatedAtDesc();

    @Modifying
    @Query(value = "DELETE from post_who_likes where post_id=:postId",
            nativeQuery = true)
    void deleteLikesFromPost(@Param("postId") int postId);

    @Modifying
    @Query(value = "DELETE from posts where id=:postId",
            nativeQuery = true)
    void deleteById(@Param("postId") int postId);

    Optional<Post> findByIdAndUser(Long id, User user);

}
