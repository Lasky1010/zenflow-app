package edu.tms.zenflow.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "post_id")
    private Long postId;

}
