package com.smartinventorymanagementsystem.adrian;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import org.junit.jupiter.api.Test;
import com.smartinventorymanagementsystem.adrian.mappers.UserMapper;
import com.smartinventorymanagementsystem.adrian.models.User;
import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;
import com.smartinventorymanagementsystem.adrian.models.Role;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    
    private UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void convertUserEntityToDTO() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("password1");
        user.setEmail("adrian@example.com");
        user.setFirstName("Adrian");
        user.setLastName("Nilsson");

        Role role = new Role();
        role.setId(1L);
        role.setName("developer");
        
        Set<User> users = new HashSet<>();
        users.add(user);
        role.setUsers(users);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles); 

        UserDTO userDTO = userMapper.toDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());

        Set<Long> originalRoleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        assertEquals(originalRoleIds, new HashSet<>(userDTO.getRoles()));


    }

    @Test
    void convertDTOToUserEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("user1");
        userDTO.setPassword("password1");
        userDTO.setEmail("adrian@example.com");
        userDTO.setFirstName("Adrian");
        userDTO.setLastName("Nilsson");
        Set<Long> roleIds = new HashSet<>();
        roleIds.add(1L);
        userDTO.setRoles(roleIds);

        User user = userMapper.toUser(userDTO);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(roleIds.size(), user.getRoles().size());
    }


}
