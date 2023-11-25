package edu.tms.zenflow.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer likes;

    private String location;

    private String description;

    @ElementCollection(targetClass = String.class)
    private Set<String> whoLikes;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,mappedBy = "post",orphanRemoval=true)
    private List<Comment> comments;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "created_at",updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

}

