package edu.tms.zenflow.data.mapper;

import edu.tms.zenflow.data.dto.comment.CommentDto;
import edu.tms.zenflow.data.entity.Comment;
import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommentMapper {

    CommentDto mapTo(Comment comment);

    List<CommentDto> mapTo(List<Comment> comments);


    @Mapping(target = "id", ignore = true)
    default Comment mapTo(CommentDto com, Post post, User user) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMessage(com.getMessage());
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setCreatedAt(OffsetDateTime.now());
        return comment;
    }
}
