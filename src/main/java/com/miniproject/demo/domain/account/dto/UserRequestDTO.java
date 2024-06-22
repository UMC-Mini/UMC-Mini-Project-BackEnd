package com.miniproject.demo.domain.account.dto;

import lombok.Getter;

public class UserRequestDTO {

    @Getter
    public static class CreateUserDTO{

        private String name;

        private String email;

        private String password;

        private String nickname;
    }

    @Getter
    public static class UpdateUserDTO{
        private String password;
        private String nickname;
    }
}
