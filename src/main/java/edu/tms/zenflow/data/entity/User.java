package edu.tms.zenflow.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.tms.zenflow.data.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "users")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(length = 1000)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;
}
