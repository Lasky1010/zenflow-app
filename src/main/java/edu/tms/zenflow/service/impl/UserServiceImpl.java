package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.LogInException;
import edu.tms.zenflow.data.exception.PasswordConfirmException;
import edu.tms.zenflow.data.exception.UsernameAlreadyTakenException;
import edu.tms.zenflow.data.mapper.UserMapper;
import edu.tms.zenflow.repository.UserRepository;
import edu.tms.zenflow.security.JwtTokenProvider;
import edu.tms.zenflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username {%s} not found", username)));

        List<? extends GrantedAuthority> authorities = user.getPermissions().stream()
                .map(a -> new SimpleGrantedAuthority(a.name())).toList();

        user.setAuthorities(authorities);
        return user;
    }

    @Override
    @Transactional
    public String auth(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername()).orElse(null);

        if (user == null || !userDto.getPassword().equals(user.getPassword())) {
            throw new LogInException("Username or password is incorrect");
        }
        return tokenProvider.generateToken(user);
    }


    @Override
    @Transactional
    public UserSignInDto signIn(UserSignInDto user) {
        User fromBD = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (fromBD != null) {
            throw new UsernameAlreadyTakenException("Username has already taken");
        }

        User fromBd = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (fromBd != null) {
            throw new UsernameAlreadyTakenException("Username has already taken");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new PasswordConfirmException("Passwords are not equal");
        }
        User save = userRepository.save(userMapper.mapTo(user));
        return userMapper.mapToSignIn(save);
    }


}
