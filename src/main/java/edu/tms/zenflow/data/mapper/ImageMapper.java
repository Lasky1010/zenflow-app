package edu.tms.zenflow.data.mapper;

import edu.tms.zenflow.data.dto.ImageDto;
import edu.tms.zenflow.data.entity.Image;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ImageMapper {


    default Image mapTo(Long userId, byte[] imgData, String name) {
        Image image = new Image();
        image.setImageData(imgData);
        image.setUserId(userId);
        image.setName(name);
        return image;
    }


    default Image mapTo(byte[] imgData, String name, Long postId) {
        Image image = new Image();
        image.setImageData(imgData);
        image.setPostId(postId);
        image.setName(name);
        return image;
    }


    ImageDto mapTo(Image image);
}
