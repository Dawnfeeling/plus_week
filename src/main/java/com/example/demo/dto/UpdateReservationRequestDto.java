package com.example.demo.dto;

import lombok.Getter;

@Getter
public class UpdateReservationRequestDto {

    private final String status;

    public UpdateReservationRequestDto(String status) {
        this.status = status;
    }
}
