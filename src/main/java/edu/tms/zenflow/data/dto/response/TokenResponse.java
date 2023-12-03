package edu.tms.zenflow.data.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@ResponseBody
@AllArgsConstructor
public class TokenResponse {
    private String token;
}
