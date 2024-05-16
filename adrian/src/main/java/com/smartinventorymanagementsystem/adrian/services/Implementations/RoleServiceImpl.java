package com.smartinventorymanagementsystem.adrian.services.Implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartinventorymanagementsystem.adrian.dtos.RoleDTO;
import com.smartinventorymanagementsystem.adrian.models.Role;
import com.smartinventorymanagementsystem.adrian.repositories.RoleRepository;
import com.smartinventorymanagementsystem.adrian.repositories.UserRepository;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.RoleService;
import com.smartinventorymanagementsystem.adrian.mappers.RoleMapper;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO findById(Long id) {
        logger.info("Fetching role by id: {}", id);
        Role role = fetchRoleById(id);
        return roleMapper.toDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        logger.info("Fetching all roles");
        List<Role> roles = roleRepository.findAll();
        logger.debug("Fetched {} roles", roles.size());
        return roles.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        logger.info("Saving role: {}", roleDTO);
        Role role = roleMapper.toRole(roleDTO);
        Role savedRole = roleRepository.save(role);
        logger.debug("Role saved with id: {}", savedRole.getId());
        return roleMapper.toDTO(savedRole);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {
        logger.info("Updating role with id: {}", roleDTO.getId());
        Role role = fetchRoleById(roleDTO.getId());
        Role updatedRole = updateFields(role, roleDTO);
        logger.debug("Role updated with id: {}", updatedRole.getId());
        return roleMapper.toDTO(roleRepository.save(updatedRole));
    }

    @Override
    public void deleteRole(Long id) {
        logger.info("Deleting role with id: {}", id);
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            logger.debug("Role with id: {} deleted", id);
        } else {
            logger.warn("Role with id: {} not found for deletion", id);
            throw new EntityNotFoundException("Role with id: " + id + " not found");
        }
    }

    private Role fetchRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> {
            logger.warn("Role with id: {} not found", id);
            return new EntityNotFoundException("Role with id: " + id + " not found");
        });
    }

    private Role updateFields(Role role, RoleDTO roleDTO) {
        role.setName(roleDTO.getName());
        role.getUsers().clear();
        for (Long userId : roleDTO.getUserIds()) {
            userRepository.findById(userId).ifPresent(user -> role.getUsers().add(user));
        }
        return role;
    }
}
