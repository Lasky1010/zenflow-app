package edu.tms.zenflow.data.dto.post;

import edu.tms.zenflow.data.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String username;

    private List<Comment> comments;

}
