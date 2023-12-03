package edu.tms.zenflow.data.dto.user;

import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.enums.Authorities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String username;

    private String email;

    private byte[] imageData;

    private String password;

    private List<Post> posts;

    private Set<Authorities> permissions = new HashSet<>();

    private Collection<? extends GrantedAuthority> authorities;

    private OffsetDateTime createdAt;

    public UserDto(Long id, String name, String username,
                   String email, String password,
                   List<Post> posts,
                   Collection<? extends GrantedAuthority> authorities,
                   OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.posts = posts;
        this.permissions.add(Authorities.ROLE_USER);
        this.authorities = authorities;
        this.createdAt = createdAt;
    }

    public UserDto(UserDto userDto) {
        this.id = userDto.id;
        this.name = userDto.name;
        this.username = userDto.username;
        this.email = userDto.email;
        this.password = userDto.password;
        this.posts = userDto.posts;
        this.permissions.add(Authorities.ROLE_USER);
        this.authorities = userDto.authorities;
        this.createdAt = userDto.createdAt;
        this.imageData = userDto.imageData;

    }

}
