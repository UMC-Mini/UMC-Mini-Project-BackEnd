package com.miniproject.demo.domain.account.service;

import com.miniproject.demo.domain.account.converter.UserConverter;
import com.miniproject.demo.domain.account.dto.LoginRequestDTO;
import com.miniproject.demo.domain.account.dto.LoginResponseDTO;
import com.miniproject.demo.domain.account.dto.UserRequestDTO;
import com.miniproject.demo.domain.account.entity.User;
import com.miniproject.demo.domain.account.repository.UserRepository;
import com.miniproject.demo.global.config.PrincipalDetails;
import com.miniproject.demo.global.config.jwt.JWTUtil;
import com.miniproject.demo.global.error.handler.AuthHandler;
import com.miniproject.demo.global.error.handler.UserHandler;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    @Override
    public User createUser(UserRequestDTO.CreateUserDTO dto) {
        User user = UserConverter.toUser(dto, passwordEncoder);

        return userRepository.save(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        // 회원 정보 존재 하는지 확인
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserHandler(ErrorStatus._NOT_FOUND_USER));

        // 회원 pw 일치 여부
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserHandler(ErrorStatus._PASSWORD_NOT_EQUAL);
        }

        // role 기본화
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("ROLE_USER");  // 기본 role 설정
        }

        PrincipalDetails principalDetails = new PrincipalDetails(user);

        // JWT 토큰 생성
        String token = jwtUtil.createAccessToken(principalDetails);

        return LoginResponseDTO.from(user, token);
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            String accessToken = jwtUtil.resolveAccessToken(request);

            // 토큰 유효성 검사
            if (!jwtUtil.isTokenValid(accessToken)) {
                throw new AuthHandler(ErrorStatus._AUTH_INVALID_TOKEN);
            }

            String userEmail = jwtUtil.getEmail(accessToken);
            log.info("User logged out: {}", userEmail);

        } catch (ExpiredJwtException e) {
            throw new AuthHandler(ErrorStatus._AUTH_EXPIRE_TOKEN);
        } catch (Exception e) {
            throw new AuthHandler(ErrorStatus._AUTH_INVALID_TOKEN);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserHandler(ErrorStatus._NOT_FOUND_USER));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(Authentication authentication, UserRequestDTO.UpdateUserDTO updateUserDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserHandler(ErrorStatus._NOT_FOUND_USER));

        if (authentication == null) {
            throw new UserHandler(ErrorStatus._AUTHENTICATION_FAILED);
        }

        if(updateUserDTO.getPassword() != "" && updateUserDTO.getNickname() != ""){
            user.update(updateUserDTO.getPassword(), updateUserDTO.getNickname(), passwordEncoder);
        }else if(updateUserDTO.getPassword() != ""){
            user.updatePw(updateUserDTO.getPassword(), passwordEncoder);
        }else if(updateUserDTO.getNickname() != ""){
            user.updateNickName(updateUserDTO.getNickname());
        }else{
            throw new UserHandler(ErrorStatus._AUTHENTICATION_FAILED);
        }

        return user;
    }
}
