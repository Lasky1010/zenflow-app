package edu.tms.zenflow.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtSecretToken {
    private static final Key SECRET_KEY = Jwts.SIG.HS512.key().build();

    public static Key getSecretKey() {
        return SECRET_KEY;
    }
}
