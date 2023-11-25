package edu.tms.zenflow.mapper;

import edu.tms.zenflow.data.dto.CommentDto;
import edu.tms.zenflow.data.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommentMapper {


    CommentDto mapTo(Comment comment);

    @Mapping(target = "id", ignore = true)
    Comment mapTo(CommentDto comment);
}
