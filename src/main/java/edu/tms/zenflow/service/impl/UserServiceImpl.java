package edu.tms.zenflow.service.impl;

import edu.tms.zenflow.data.constants.BadRequestConstants;
import edu.tms.zenflow.data.dto.image.ImageDto;
import edu.tms.zenflow.data.dto.user.UserDto;
import edu.tms.zenflow.data.dto.user.UserSignUpDto;
import edu.tms.zenflow.data.dto.user.UserUpdateDto;
import edu.tms.zenflow.data.entity.User;
import edu.tms.zenflow.data.exception.*;
import edu.tms.zenflow.data.mapper.UserMapper;
import edu.tms.zenflow.repository.UserRepository;
import edu.tms.zenflow.service.ImageService;
import edu.tms.zenflow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

import static edu.tms.zenflow.data.constants.BadRequestConstants.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    public UserSignUpDto signUp(UserSignUpDto user) {

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
    public UserDto getCurrentUser(Principal principal) {
        User byPrincipal = findByPrincipal(principal);
        ImageDto imageByUserId = imageService.getImageByUserId(byPrincipal.getId());
        byte[] imageData = imageByUserId != null ? imageByUserId.getImageData() : null;
        UserDto userDto = userMapper.mapTo(byPrincipal);
        userDto.setImageData(imageData);
        return userDto;
    }

    @Override
    public UserUpdateDto update(UserUpdateDto userUpdate, Principal principal) {
        User user = findByPrincipal(principal);
        List<String> allUsernames = userRepository.findAll().stream().map(User::getUsername).toList();
        List<String> allEmails = userRepository.findAll().stream().map(User::getEmail).toList();
        User updated;
        try {
            User user1 = userMapper.mapToUpdatedUser(userUpdate, user);
            log.info("try to update user");
            if (userUpdate.getUsername() != null
                    && allUsernames.contains(userUpdate.getUsername())) {
                throw new UsernameAlreadyTakenException(BadRequestConstants.USERNAME_ALREADY_TAKEN);
            }
            if (userUpdate.getEmail() != null
                    && allEmails.contains(userUpdate.getEmail())) {
                throw new EmailHasAlreadyTakenException(BadRequestConstants.EMAIL_ALREADY_TAKEN);
            }
            updated = userRepository.save(user1);
        } catch (Exception exception) {
            log.error("Something went wrong\n"
                    + exception.getMessage());
            throw new BadRequestException(USERNAME_OR_EMAIL_ALREADY_TAKEN);
        }

        return userMapper.mapToUpdatedDto(updated);
    }

    @Override
    public UserDto getUserById(Long id) {
        ImageDto imageByUserId = imageService.getImageByUserId(id);
        byte[] imageData = imageByUserId != null ? imageByUserId.getImageData() : null;
        UserDto userDto = userMapper.mapTo(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND)));
        userDto.setImageData(imageData);
        return userDto;

    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userMapper.mapTo(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found")));
    }

    @Override
    public UserDto subscribe(Long id, Principal principal) {
        var user = findByPrincipal(principal);
        var wantToSubscribe = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!user.getOnWhoSubscribe().contains(wantToSubscribe)) {
            user.getOnWhoSubscribe().add(wantToSubscribe);
            wantToSubscribe.getSubscribers().add(user);
        } else {
            user.getOnWhoSubscribe().remove(wantToSubscribe);
            wantToSubscribe.getSubscribers().remove(user);
        }
        userRepository.save(user);
        User save = userRepository.save(wantToSubscribe);
        UserDto userDto = userMapper.mapTo(save);
        ImageDto imageByUserId = imageService.getImageByUserId(id);
        byte[] imageData = imageByUserId != null ? imageByUserId.getImageData() : null;
        userDto.setImageData(imageData);
        return userDto;
    }

    @Transactional
    public User findByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND));
    }

}
