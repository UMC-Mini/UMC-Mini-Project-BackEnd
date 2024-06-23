package com.miniproject.demo.domain.account.controller;

import com.miniproject.demo.domain.account.converter.UserConverter;
import com.miniproject.demo.domain.account.dto.LoginRequestDTO;
import com.miniproject.demo.domain.account.dto.LoginResponseDTO;
import com.miniproject.demo.domain.account.dto.UserRequestDTO;
import com.miniproject.demo.domain.account.dto.UserResponseDTO;
import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.account.service.UserService;
import com.miniproject.demo.domain.post.converter.PostConverter;
import com.miniproject.demo.domain.post.dto.PostRequestDTO;
import com.miniproject.demo.domain.post.entity.Post;
import com.miniproject.demo.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<UserResponseDTO.JoinResultDTO> createPost(@RequestBody @Valid UserRequestDTO.CreateUserDTO request) {
        User user = userService.createUser(request);
        return BaseResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request){
        return BaseResponse.onSuccess(userService.login(request));
    }

    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return BaseResponse.onSuccess("삭제 되었습니다.");
    }

    @PatchMapping("/{userId}")
    public BaseResponse<UserResponseDTO.UserPreviewDTO> updateUser(Authentication authentication, @RequestBody UserRequestDTO.UpdateUserDTO updateUserDTO, @PathVariable Long userId){
        User user = userService.updateUser(authentication, updateUserDTO, userId);
        return BaseResponse.onSuccess(UserConverter.toUserPreviewDTO(user));
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request){
        userService.logout(request);
        return BaseResponse.onSuccess("로그아웃 성공");
    }

}
