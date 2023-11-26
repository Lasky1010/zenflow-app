package edu.tms.zenflow.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String name;

    private String username;

    private String email;

    private String password;

    private String bio;

}
