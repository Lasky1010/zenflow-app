package edu.tms.zenflow.mapper;

import edu.tms.zenflow.data.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommentDto {

    @Mapping(target = "createdAt",ignore = true)
    CommentDto mapTo(Comment comment);

    Comment mapTo(CommentDto comment);
}
