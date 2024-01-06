package edu.tms.zenflow.repository;

import edu.tms.zenflow.data.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image,Long> {


    Optional<Image> findImageByUserId(Long userId);

    Optional<Image> findByPostId(Long postId);

}
