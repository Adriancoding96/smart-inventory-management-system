package com.smartinventorymanagementsystem.adrian.services.Interfaces;
import com.smartinventorymanagementsystem.adrian.models.Role;
import com.smartinventorymanagementsystem.adrian.dtos.RoleDTO;
import java.util.List;


public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO findById(Long id);
    RoleDTO saveRole(RoleDTO roleDTO);
    RoleDTO updateRole(RoleDTO roleDTO);
    void deleteRole(Long id);
}
