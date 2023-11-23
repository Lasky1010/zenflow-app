package edu.tms.zenflow.data.dto;

import edu.tms.zenflow.data.entity.Post;

public class CommentDto {

    private Long id;

    private Post post;

    private String username;

    private Long userId;

    private String message;
}
