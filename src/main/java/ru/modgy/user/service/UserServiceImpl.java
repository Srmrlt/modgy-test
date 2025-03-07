package ru.modgy.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.modgy.exception.AccessDeniedException;
import ru.modgy.exception.NotFoundException;
import ru.modgy.user.dto.NewUserDto;
import ru.modgy.user.dto.UpdateUserDto;
import ru.modgy.user.dto.UserDto;
import ru.modgy.user.dto.mapper.UserMapper;
import ru.modgy.user.model.Roles;
import ru.modgy.user.model.User;
import ru.modgy.user.repository.UserRepository;
import ru.modgy.utility.EntityService;
import ru.modgy.utility.UtilityService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EntityService entityService;
    private final UtilityService utilityService;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers(Long requesterId, Boolean isActive) {
        User requester = entityService.getUserIfExists(requesterId);

        List<Roles> roles =
                Arrays.asList(Roles.values()).subList(requester.getRole().ordinal(), Roles.values().length);

        List<User> allUsers;
        if (Objects.isNull(isActive)) {
            allUsers = userRepository.findAllByRoleIn(roles).orElse(Collections.emptyList());
        } else {
            allUsers = userRepository.findAllByRoleInAndIsActive(roles, isActive).orElse(Collections.emptyList());
        }
        log.info("UserService: getAllUsers, requesterId={}, isActive={}, usersList size={}",
                requesterId, isActive, allUsers.size());
        return userMapper.map(allUsers);
    }

    @Transactional()
    @Override
    public UserDto addUser(Long requesterId, NewUserDto newUserDto) {
        User newUser = userMapper.toUser(newUserDto);
        User addedUser = userRepository.save(newUser);
        log.info("userService: addUser, requesterId={}, newUserDto={},  newUser={}",
                requesterId, newUserDto, addedUser);
        return userMapper.toUserDto(addedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(Long requesterId, Long userId) {
        User user = entityService.getUserIfExists(userId);
        log.info("UserService: getUserById, requesterId ={}, by userId={}", requesterId, userId);
        return userMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long requesterId, Long userId) {
        User user = entityService.getUserIfExists(userId);

        if (user.getRole().equals(Roles.ROLE_BOSS)) {
            throw new AccessDeniedException("User with role=ROLE_BOSS can't delete");
        }

        Integer result = userRepository.deleteUserById(userId);
        if (result == 0) {
            throw new NotFoundException(String.format("user with id=%d not found", userId));
        }
        log.info("UserService: deleteUserById, requesterId={} userId={}", requesterId, user);
    }

    @Transactional
    @Override
    public UserDto updateUser(Long requesterId, Long userId, UpdateUserDto updateUserDto) {
        boolean updateBySelf = utilityService.checkRequesterRequestsHimself(requesterId, userId);
        User requester = entityService.getUserIfExists(requesterId);

        User newUser = userMapper.toUser(updateUserDto);
        newUser.setId(userId);
        User oldUser;

        if (updateBySelf) {
            oldUser = requester;
        } else {
            utilityService.checkHigherOrdinalRoleAccessForUsers(requester, newUser);
            oldUser = entityService.getUserIfExists(userId);
        }

        if (Objects.isNull(newUser.getLastName())) {
            newUser.setLastName(oldUser.getLastName());
        }
        if (Objects.isNull(newUser.getFirstName())) {
            newUser.setFirstName(oldUser.getFirstName());
        }
        if (Objects.isNull(newUser.getMiddleName())) {
            newUser.setMiddleName(oldUser.getMiddleName());
        }
        if (Objects.isNull(newUser.getPassword())) {
            newUser.setPassword(oldUser.getPassword());
        }
        if (Objects.isNull(newUser.getEmail())) {
            newUser.setEmail(oldUser.getEmail());
        }
        if (Objects.isNull(newUser.getRole()) || updateBySelf) {
            newUser.setRole(oldUser.getRole());
        }
        if (Objects.isNull(newUser.getIsActive()) || updateBySelf) {
            newUser.setIsActive(oldUser.getIsActive());
        }
        User updatedUser = userRepository.save(newUser);
        log.info("UserService: updateUser, requesterId={}, userId={}, to updateUserDto={}",
                requesterId, userId, updateUserDto);

        return userMapper.toUserDto(updatedUser);
    }

    @Override
    public UserDto setUserState(Long requesterId, Long userId, Boolean isActive) {
        User user = entityService.getUserIfExists(userId);

        user.setIsActive(isActive);

        User updatedUser = userRepository.save(user);
        return userMapper.toUserDto(updatedUser);
    }
}
