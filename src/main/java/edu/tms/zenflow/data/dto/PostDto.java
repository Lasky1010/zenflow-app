package edu.tms.zenflow.data.dto;

import edu.tms.zenflow.data.entity.Comment;
import edu.tms.zenflow.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
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
}
