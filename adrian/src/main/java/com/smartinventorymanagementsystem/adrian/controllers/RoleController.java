package com.smartinventorymanagementsystem.adrian.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smartinventorymanagementsystem.adrian.dtos.RoleDTO;
import com.smartinventorymanagementsystem.adrian.services.Interfaces.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/sims/api/v1")
@PreAuthorize("hasRole(ADMIN)")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> GetAllRoles(){
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(roles);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id){
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(roleDTO));
    }

    @PutMapping("/roles")
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.updateRole(roleDTO));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }



}
