package com.smartinventorymanagementsystem.adrian.services.Implementations;

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;
import com.smartinventorymanagementsystem.adrian.models.User;
import com.smartinventorymanagementsystem.adrian.models.Role;

import com.smartinventorymanagementsystem.adrian.mappers.UserMapper;
import com.smartinventorymanagementsystem.adrian.repositories.RoleRepository;
import com.smartinventorymanagementsystem.adrian.repositories.UserRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.RoleService;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    

    private UserRepository userRepository;
    
    private RoleRepository roleRepository;
    
    private UserMapper userMapper;

    private RoleService roleService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleService             roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return userMapper.toDTO(user.get());
        } else {
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }  
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setRoles(fetchAndApplyRoles(userDTO.getRoles()));
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getId());
        if(user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setRoles(fetchAndApplyRoles(userDTO.getRoles()));
            User updatedUser = updateFields(userToUpdate, userDTO);
            return userMapper.toDTO(userRepository.save(updatedUser));

        } else {
            throw new EntityNotFoundException("User with id: " + userDTO.getId()+ " not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    
    private User updateFields(User user, UserDTO userDTO) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(user.getLastName());

        Set<Role> updatedRoles = new HashSet<>();
        for(Long roleId : userDTO.getRoles()) {
            updatedRoles.add(roleRepository.findById(roleId).get());
        }
        user.setRoles(updatedRoles);

        return user;
    }

    private Set<Role> fetchAndApplyRoles(Set<Long> roleIds) {
        if(roleIds == null || roleIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Role> roles = new HashSet<>();
        for(Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));
            roles.add(role);
        }
        return roles;
    }

}
