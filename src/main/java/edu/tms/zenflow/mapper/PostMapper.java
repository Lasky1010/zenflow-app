package edu.tms.zenflow.mapper;

import edu.tms.zenflow.data.dto.PostDto;
import edu.tms.zenflow.data.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PostMapper {

    @Mapping(target = "createdAt",ignore = true)
    PostDto mapTo(Post post);

    Post mapTo(PostDto post);
}
