package edu.tms.zenflow.data.dto;

import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.enums.Authorities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.OffsetDateTime;
import java.util.Collection;
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

    private String password;

    private List<Post> posts;

    private Set<Authorities> userAuthorities;

    private Collection<? extends GrantedAuthority> authorities;

    private OffsetDateTime createdAt;
}
