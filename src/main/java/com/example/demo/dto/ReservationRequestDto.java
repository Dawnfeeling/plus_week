package com.example.demo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequestDto {
    private final Long itemId;
    private final Long userId;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public ReservationRequestDto(Long itemId, Long userId, LocalDateTime startAt, LocalDateTime endAt) {
        this.itemId = itemId;
        this.userId = userId;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
