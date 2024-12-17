package com.example.demo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    @DisplayName("encode 메소드 test")
    void testEncode() {
        // Given
        String rawPassword = "myPassword";

        // When
        String encodedPassword = PasswordEncoder.encode(rawPassword);
        String encodedPassword2 = PasswordEncoder.encode(rawPassword);

        // Then
        assertNotNull(encodedPassword, "인코드된 비밀번호는 null이 아니어야 한다.");
        assertNotEquals(rawPassword, encodedPassword, "기존 비밀번호와 인코드된 비밀번호는 달라야 한다.");
        assertNotEquals(rawPassword, encodedPassword2, "똑같은 비밀번호를 인코드한 결과는 달라야 한다.");
    }


    @Test
    @DisplayName("matches 메소드 test")
    void testMatches(){
        // Given
        String rawPassword = "myPassword";
        String encodedPassword = PasswordEncoder.encode(rawPassword);
        String rawPassword2 = "myPassword2";

        // When
        boolean matches = PasswordEncoder.matches(rawPassword, encodedPassword);
        boolean matches2 = PasswordEncoder.matches(rawPassword2, encodedPassword);

        // Then
        assertTrue(matches, "기존 비밀번호와 인코드된 비밀번호를 매치했을때 true를 반환해야 한다.");
        assertFalse(matches2, "다른 비밀번호와 매치했을 때 false를 반환해야 한다.");
    }
}