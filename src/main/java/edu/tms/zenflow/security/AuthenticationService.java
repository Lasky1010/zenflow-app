package edu.tms.zenflow.security;

import edu.tms.zenflow.data.constants.SecurityConstants;
import edu.tms.zenflow.data.dto.user.UserLogInDto;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.LogInException;
import edu.tms.zenflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final JwtTokenVerification jwtTokenVerification;

    public String auth(UserLogInDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername()).orElse(null);

        if (user == null || !matchesEncodedPassword(userDto.getPassword(), user.getPassword())) {
            throw new LogInException("Username or password is incorrect");
        }
        String token = SecurityConstants.PREFIX + " " + tokenProvider.generateToken(user);

        SecurityContextHolder.getContext().setAuthentication(jwtTokenVerification.fromToken(token));
        return token;
    }


    private boolean matchesEncodedPassword(String dtoPass, String passFromDB) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(dtoPass, passFromDB);
    }

}
