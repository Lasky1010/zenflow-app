package edu.tms.zenflow.data.dto.request;

import edu.tms.zenflow.annotation.ValidPass;
import edu.tms.zenflow.annotation.ValidUsername;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogInDto {

    @NotEmpty(message = "Username cant be empty")
    @ValidUsername
    private String username;

    @ValidPass
    @NotEmpty(message = "Password cant be empty")
    private String password;
}
