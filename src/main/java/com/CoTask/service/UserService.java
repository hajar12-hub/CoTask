
package com.CoTask.service;
import com.CoTask.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
    UserResponseDTO updateUser(Long id, UserDTO userDTO);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByUsername(String username);
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getUsersByRole(String role);
    void deleteUser(Long id);
}