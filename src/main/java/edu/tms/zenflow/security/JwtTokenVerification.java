package edu.tms.zenflow.security;

import edu.tms.zenflow.data.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtTokenVerification {

    // private Key jwtSecretCode=Jwts.SIG.HS512.key().build();
    private final Key jwtSecretKey = JwtSecretToken.getSecretKey();

    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(rawToken(token));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String rawToken(String token) {
        if (token != null && token.startsWith(SecurityConstants.PREFIX)) {
            return token.substring(SecurityConstants.PREFIX.length() + 1);
        }
        return null;
    }

    public Authentication fromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(rawToken(token));

        Claims claims = claimsJws.getPayload();
        var username = (String) claims.get("username");
        var perms = (ArrayList<String>) claims.get("perms");

        List<? extends GrantedAuthority> permsList = perms.stream()
                .map(SimpleGrantedAuthority::new).toList();

        return new UsernamePasswordAuthenticationToken(username, null, permsList);


    }
}
