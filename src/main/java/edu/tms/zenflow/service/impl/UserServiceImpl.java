package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.repository.UserRepository;
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
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username {%s} not found", username)));

        List<? extends GrantedAuthority> authorities = user.getUserAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.name())).toList();

        user.setAuthorities(authorities);
        return user;
    }
}
