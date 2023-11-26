package edu.tms.zenflow.data.dto.request;

import edu.tms.zenflow.annotation.ValidEmail;
import edu.tms.zenflow.annotation.ValidPass;
import edu.tms.zenflow.annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInDto {

    @ValidUsername
    @NotEmpty(message = "Username is required")
    private String username;

    @Email(message = "It's not email")
    @ValidEmail
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @ValidPass
    private String password;

    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;

}
