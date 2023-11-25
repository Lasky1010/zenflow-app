package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.dto.UserDto;
import edu.tms.zenflow.data.entity.User;
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

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username {%s} not found", username)));

        List<? extends GrantedAuthority> authorities = user.getUserAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.name())).toList();

        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public String auth(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow();

        if (!userDto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException();
        }
        return tokenProvider.generateToken(user);
    }
}
