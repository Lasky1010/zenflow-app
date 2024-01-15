package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.dto.image.ImageDto;
import edu.tms.zenflow.data.entity.Image;
import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.ImageNotFoundException;
import edu.tms.zenflow.data.exception.PostNotFoundException;
import edu.tms.zenflow.data.mapper.ImageMapper;
import edu.tms.zenflow.repository.ImageRepository;
import edu.tms.zenflow.repository.UserRepository;
import edu.tms.zenflow.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static edu.tms.zenflow.data.constants.BadRequestConstants.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ImageMapper mapper;


    @Override
    public ImageDto uploadImageToUser(MultipartFile img, Principal principal) throws IOException {
        User byPrincipal = findByPrincipal(principal);
        Image userProfileImage = imageRepository.findImageByUserId(byPrincipal.getId()).orElse(null);

        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        Image image = mapper.mapTo(byPrincipal.getId(), img.getBytes(), img.getOriginalFilename());
        Image save = imageRepository.save(image);
        return mapper.mapTo(save);
    }

    @Override
    public ImageDto uploadImageToPost(MultipartFile img, Long postId, Principal principal) throws IOException {
        User byPrincipal = findByPrincipal(principal);
        Post post = byPrincipal.getPosts()
                .stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst().orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));
        Image image = mapper.mapTo(compressBytes(img.getBytes()), img.getOriginalFilename(), post.getId());
        return mapper.mapTo(imageRepository.save(image));
    }

    @Override
    public ImageDto getImageToUser(Principal principal) {
        User user = findByPrincipal(principal);

        Image image = imageRepository.findImageByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(image)) {
            image.setImageData(image.getImageData());
        }

        return mapper.mapTo(image);
    }

    @Override
    public ImageDto getImageToPost(Long postId) {
        Image image = imageRepository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post: " + postId));
        if (!ObjectUtils.isEmpty(image)) {
            image.setImageData(decompressBytes(image.getImageData()));
        }

        return mapper.mapTo(image);
    }


    public User findByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found exception"));
    }



    @Override
    public ImageDto getImageByUserId(Long id) {
        return mapper.mapTo(imageRepository.findImageByUserId(id).orElse(null));
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            log.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }
}
