package com.miniproject.demo.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String nickname;
}
