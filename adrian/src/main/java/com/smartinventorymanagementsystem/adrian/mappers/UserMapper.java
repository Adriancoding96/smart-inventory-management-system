package com.smartinventorymanagementsystem.adrian.mappers;

import com.smartinventorymanagementsystem.adrian.models.User;
import com.smartinventorymanagementsystem.adrian.repositories.RoleRepository;

import org.hibernate.MappingException;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartinventorymanagementsystem.adrian.models.Role;
import java.util.stream.Collectors;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.RoleService;
import java.util.Collections;
import java.util.HashSet;

import jakarta.persistence.EntityNotFoundException;

import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;

@Service
public class UserMapper {

    private ModelMapper modelMapper;
    private RoleRepository roleRepository;

    @Autowired
    public UserMapper(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        configureModelMapper();
    }

    private void configureModelMapper() {
        Converter<Set<Role>, Set<Long>> roleToRoleIdConverter = new Converter<Set<Role>, Set<Long>>() {
            @Override
            public Set<Long> convert(MappingContext<Set<Role>, Set<Long>> context) {
                return context.getSource().stream().map(Role::getId).collect(Collectors.toSet());
            }
        };

        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                using(roleToRoleIdConverter).map(source.getRoles(), destination.getRoles());
            }
        });
    }


    public UserDTO toDTO(User user) {
        try {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return userDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map User to UserDTO", exception);
        }
    }

    public User toUser(UserDTO dto) {
        try {
            User user = modelMapper.map(dto, User.class);
            user.setRoles(fetchAndApplyRoles(dto.getRoles()));
            return user;
        } catch (Exception exception) {
            throw new MappingException("Unable to map UserDTO to User", exception);
        }

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
