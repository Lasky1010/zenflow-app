package edu.tms.zenflow.service;

import edu.tms.zenflow.data.dto.image.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface ImageService {
    ImageDto uploadImageToUser(MultipartFile img, Principal principal) throws IOException;

    ImageDto uploadImageToPost(MultipartFile img, Long postId, Principal principal) throws IOException;

    ImageDto getImageToUser(Principal principal);

    ImageDto getImageToPost(Long postId);

    ImageDto getImageByUserId(Long id);
}
