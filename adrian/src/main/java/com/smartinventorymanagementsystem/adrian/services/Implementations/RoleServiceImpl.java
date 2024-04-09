package com.smartinventorymanagementsystem.adrian.services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartinventorymanagementsystem.adrian.dtos.RoleDTO;
import com.smartinventorymanagementsystem.adrian.models.Role;
import com.smartinventorymanagementsystem.adrian.repositories.RoleRepository;
import com.smartinventorymanagementsystem.adrian.repositories.UserRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.RoleService;
import java.util.List;
import com.smartinventorymanagementsystem.adrian.mappers.RoleMapper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()) {
            return roleMapper.toDTO(role.get());
        } else {
            throw new EntityNotFoundException("Role with id: " + id + " not found");
        }
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                    .map(roleMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        Role role = roleRepository.save(roleMapper.toRole(roleDTO));
        return roleMapper.toDTO(role);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        Optional<Role> role = roleRepository.findById(roleDTO.getId());
        if(role.isPresent()) {
            Role updatedRole = updateFields(role.get(), roleDTO);
            return roleMapper.toDTO(roleRepository.save(updatedRole));
        } else {
            throw new EntityNotFoundException("Role with id: " + roleDTO.getId() + " not found");
        }
    } 

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private Role updateFields(Role role, RoleDTO roleDTO) {
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        for(Long userId: roleDTO.getUserIds()) {
            role.getUsers().add(userRepository.findById(userId).get());
        }
        return role;
    }

}
