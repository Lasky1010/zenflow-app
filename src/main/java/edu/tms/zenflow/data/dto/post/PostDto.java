package edu.tms.zenflow.data.dto.post;

import edu.tms.zenflow.data.entity.Comment;
import edu.tms.zenflow.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    private Integer likes;

    private String location;

    private String description;

    private Set<String> whoLikes;

    private User user;

    private List<Comment> comments;

    private OffsetDateTime createdAt;
}
