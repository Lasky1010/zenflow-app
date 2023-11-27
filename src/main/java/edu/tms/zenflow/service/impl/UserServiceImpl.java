package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.constants.BadRequestConstants;
import edu.tms.zenflow.data.dto.request.UserSignInDto;
import edu.tms.zenflow.data.dto.request.UserUpdateDto;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.BadRequestException;
import edu.tms.zenflow.data.exception.EmailHasAlreadyTakenException;
import edu.tms.zenflow.data.exception.PasswordConfirmException;
import edu.tms.zenflow.data.exception.UsernameAlreadyTakenException;
import edu.tms.zenflow.data.mapper.UserMapper;
import edu.tms.zenflow.repository.UserRepository;
import edu.tms.zenflow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

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
    public UserSignInDto signUp(UserSignInDto user) {

        User fromBd = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (fromBd != null) {
            throw new UsernameAlreadyTakenException(BadRequestConstants.USERNAME_ALREADY_TAKEN);
        }
        fromBd = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (fromBd != null) {
            throw new EmailHasAlreadyTakenException(BadRequestConstants.EMAIL_ALREADY_TAKEN);
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new PasswordConfirmException(BadRequestConstants.PASSWORD_NOT_EQUAL);
        }
        try {
            log.info("Trying save user: " + user.getUsername());
            User save = userRepository.save(userMapper.mapTo(user));
            log.info("Successfully saved user: " + user.getUsername());
            return userMapper.mapToSignIn(save);
        } catch (Exception e) {
            log.error("Error during saving to database user: " + user.getUsername() +
                    "{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return findByPrincipal(principal);
    }

    @Override
    @Transactional
    public UserUpdateDto update(UserUpdateDto userUpdate, Principal principal) {
        User user = getCurrentUser(principal);

        User updated;
        try {
            User user1 = userMapper.mapToUpdatedUser(userUpdate, user);
            log.info("try to update user");

            if (userUpdate.getUsername() != null
                    && userRepository.findByUsername(user1.getUsername()).orElse(null) != null) {
                throw new UsernameAlreadyTakenException(BadRequestConstants.USERNAME_ALREADY_TAKEN);
            }
            if (userUpdate.getEmail() != null
                    && userRepository.findByEmail(user1.getEmail()).orElse(null) != null) {
                throw new EmailHasAlreadyTakenException(BadRequestConstants.EMAIL_ALREADY_TAKEN);
            }

            updated = userRepository.save(user1);
        } catch (Exception exception) {
            log.error("Something went wrong\n"
                    + exception.getMessage());
            throw new BadRequestException("Username or Email has already taken");
        }

        return userMapper.mapToUpdatedDto(updated);
    }

    public User findByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found exception"));
    }

}
