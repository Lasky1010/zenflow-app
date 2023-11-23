package edu.tms.zenflow.data.dto;

import edu.tms.zenflow.data.entity.Post;
import edu.tms.zenflow.data.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String username;

    private String email;

    private String password;

    private List<Post> posts;

    private UserRole role;
}
