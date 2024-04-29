package com.smartinventorymanagementsystem.adrian.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.smartinventorymanagementsystem.adrian.dtos.RoleDTO;
import com.smartinventorymanagementsystem.adrian.models.Role;
import com.smartinventorymanagementsystem.adrian.models.User;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class RoleMapperTest {
    
    private RoleMapper roleMapper;

    @Autowired
    public RoleMapperTest(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Test
    void convertRoleEntityToDTO() {

        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        Role role = new Role();
        role.setId(1L);
        role.setName("developer");
        Set<User> users = new HashSet<>();
        users.add(user);
        role.setUsers(users);

        RoleDTO roleDTO = roleMapper.toDTO(role);

        assertEquals(role.getId(), roleDTO.getId());
        assertEquals(role.getName(), roleDTO.getName());

        Set<Long> userIds = users.stream().map(User::getId).collect(Collectors.toSet());
        assertEquals(userIds, roleDTO.getUserIds());
    }

    @Test
    void convertDTOToRoleEntity() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setName("developer");
        Set<Long> userIds = new HashSet<>();
        userIds.add(1L);
        roleDTO.setUserIds(userIds);

        Role role = roleMapper.toRole(roleDTO);

        assertEquals(roleDTO.getId(), role.getId());
        assertEquals(roleDTO.getName(), role.getName());
    }

}
