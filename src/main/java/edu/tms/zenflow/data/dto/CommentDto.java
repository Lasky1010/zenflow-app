package edu.tms.zenflow.data.dto;

import edu.tms.zenflow.data.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private Post post;

    private String username;

    private Long userId;

    private String message;
}
