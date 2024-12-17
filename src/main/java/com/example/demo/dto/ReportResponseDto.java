package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportResponseDto {

    private final List<Long> userIds;

    public ReportResponseDto(List<Long> userIds) {
        this.userIds = userIds;
    }
}
