package edu.tms.zenflow.mapper;

import edu.tms.zenflow.data.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommentMapper {

    @Mapping(target = "createdAt",ignore = true)
    CommentMapper mapTo(Comment comment);

    Comment mapTo(CommentMapper comment);
}
