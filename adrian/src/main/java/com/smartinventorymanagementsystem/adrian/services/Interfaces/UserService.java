package com.smartinventorymanagementsystem.adrian.services.Interfaces;
import com.smartinventorymanagementsystem.adrian.dtos.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO saveUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Long id);
}
