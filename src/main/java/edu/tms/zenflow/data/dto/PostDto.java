package edu.tms.zenflow.data.dto;

import edu.tms.zenflow.data.entity.Comment;
import edu.tms.zenflow.data.entity.User;

import java.util.List;
import java.util.Set;

public class PostDto {
    private Long id;

    private Integer likes;

    private String location;

    private String description;

    private Set<String> whoLikes;

    private User user;

    private List<Comment> comments;
}
