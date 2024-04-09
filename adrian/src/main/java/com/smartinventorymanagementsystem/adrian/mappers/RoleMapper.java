package com.smartinventorymanagementsystem.adrian.mappers;

import org.springframework.stereotype.Service;
import org.hibernate.MappingException;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartinventorymanagementsystem.adrian.dtos.RoleDTO;
import com.smartinventorymanagementsystem.adrian.models.Role;
import com.smartinventorymanagementsystem.adrian.models.User;

import java.util.stream.Collectors;

@Service
public class RoleMapper {
    
    private ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
        Converter<Set<User>, Set<Long>> userToUserIdConverter = new Converter<Set<User>, Set<Long>>() {
            @Override
            public Set<Long> convert(MappingContext<Set<User>, Set<Long>> context) {
                return context.getSource().stream().map(User::getId).collect(Collectors.toSet());
            }
        };

        modelMapper.addMappings(new PropertyMap<Role, RoleDTO>() {
            @Override
            protected void configure() {
                using(userToUserIdConverter).map(source.getUsers(), destination.getUserIds());
            }
        });
    }

    public RoleDTO toDTO(Role role) {
        try {
            RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
            return roleDTO;
        } catch (Exception exception) {
            throw new MappingException("Unable to map User to UserDTO", exception);
        }
    }

    public Role toRole(RoleDTO dto) {
        try {
            Role role = modelMapper.map(dto, Role.class);
            return role;
        } catch (Exception exception) {
            throw new MappingException("Unable to map UserDTO to User", exception);
        }

    }


}
