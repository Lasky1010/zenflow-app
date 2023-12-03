package edu.tms.zenflow.data.mapper;

import edu.tms.zenflow.data.dto.post.PostDto;
import edu.tms.zenflow.data.dto.request.PostCreateDto;
import edu.tms.zenflow.data.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PostMapper {

    PostDto mapTo(Post post);

    Post mapTo(PostCreateDto post);

    @Mapping(target = "id", ignore = true)
    Post mapTo(PostDto post);

    List<PostDto> mapTo(List<Post> posts);


}
