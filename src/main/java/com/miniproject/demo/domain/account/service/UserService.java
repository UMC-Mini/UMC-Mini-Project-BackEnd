package com.miniproject.demo.domain.account.service;

import com.miniproject.demo.domain.account.dto.LoginRequestDTO;
import com.miniproject.demo.domain.account.dto.LoginResponseDTO;
import com.miniproject.demo.domain.account.dto.UserRequestDTO;
import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    User createUser(UserRequestDTO.CreateUserDTO dto);
    LoginResponseDTO login(LoginRequestDTO request);
    void logout(HttpServletRequest request);
    void deleteUser(Long userId);
    User updateUser(UserRequestDTO.UpdateUserDTO updateUserDTO, Long userId);
}
