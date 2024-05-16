package com.smartinventorymanagementsystem.adrian.services.Implementations;

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;
import com.smartinventorymanagementsystem.adrian.models.User;
import com.smartinventorymanagementsystem.adrian.models.Role;

import com.smartinventorymanagementsystem.adrian.mappers.UserMapper;
import com.smartinventorymanagementsystem.adrian.repositories.RoleRepository;
import com.smartinventorymanagementsystem.adrian.repositories.UserRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        logger.debug("Fetched {} users", users.size());
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        User user = fetchUserById(id);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        logger.info("Saving user: {}", userDTO);
        User user = userMapper.toUser(userDTO);
        user.setRoles(fetchAndApplyRoles(userDTO.getRoles()));
        User savedUser = userRepository.save(user);
        logger.debug("User saved with id: {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        logger.info("Updating user with id: {}", userDTO.getId());
        User user = fetchUserById(userDTO.getId());
        user.setRoles(fetchAndApplyRoles(userDTO.getRoles()));
        User updatedUser = updateFields(user, userDTO);
        logger.debug("User updated with id: {}", updatedUser.getId());
        return userMapper.toDTO(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.debug("User with id: {} deleted", id);
        } else {
            logger.warn("User with id: {} not found for deletion", id);
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }
    }

    private User fetchUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            logger.warn("User with id: {} not found", id);
            return new EntityNotFoundException("User with id: " + id + " not found");
        });
    }

    private User updateFields(User user, UserDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

    private Set<Role> fetchAndApplyRoles(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
            roles.add(role);
        }
        return roles;
    }
}
