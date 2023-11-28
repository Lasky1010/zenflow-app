package edu.tms.zenflow.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {

    private Long id;

    private String name;

    private byte[] imageData;

    private Long userId;

    private Long postId;

}
