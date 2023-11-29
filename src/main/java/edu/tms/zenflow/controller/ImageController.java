package edu.tms.zenflow.controller;

import edu.tms.zenflow.data.dto.ImageDto;
import edu.tms.zenflow.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController {

    private final ImageService imageService;


    @PostMapping
    public ResponseEntity<ImageDto> uploadToUser(@RequestParam("file") MultipartFile file,
                                                 Principal principal) throws IOException {
        ImageDto imageDto = imageService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(imageDto);

    }

    @PostMapping("/{postId}")
    public ResponseEntity<ImageDto> uploadToPost(Long postId,
                                                 @RequestParam("file") MultipartFile file,
                                                 Principal principal) throws IOException {
        ImageDto imageDto = imageService.uploadImageToPost(file, postId, principal);
        return ResponseEntity.ok(imageDto);

    }


    @GetMapping
    public ResponseEntity<ImageDto> getImageToUser(Principal principal) {
        ImageDto imageToUser = imageService.getImageToUser(principal);
        return ResponseEntity.ok(imageToUser);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<ImageDto> getImageToPost(Long postId) {
        ImageDto imageToPost = imageService.getImageToPost(postId);
        return ResponseEntity.ok(imageToPost);
    }


}
