package edu.tms.zenflow.security;

import edu.tms.zenflow.data.constants.SecurityConstants;
import edu.tms.zenflow.data.entity.User;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final String IS_REFRESHED_TOKEN = "isRefreshToken";

    private final Key secretKey = JwtSecretToken.getSecretKey();

    public String generateToken(User user) {
        log.debug("Generate jwt token for user with id={}", user.getId());

        var now = new Date(System.currentTimeMillis());
        var nowTime = now.getTime();
        var expTimeMillis = SecurityConstants.EXP_TIME;
        var expDate = new Date(nowTime + expTimeMillis);
        return Jwts.builder()
                .issuer("AlexanderKovgunov")
                .subject(user.getUsername())
                .claim("id", Long.toString(user.getId()))
                .claim("username", user.getUsername())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("perms", user.getPermissions())
                .claim(IS_REFRESHED_TOKEN, false)
                .notBefore(now)
                .issuedAt(now)
                .expiration(expDate)
                .signWith(secretKey)
                .compact();
    }
}
