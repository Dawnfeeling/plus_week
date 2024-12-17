package com.example.demo.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String email;

    private final String nickname;

    private final String role;

    public UserResponseDto(Long id, String email, String nickname, String role) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
