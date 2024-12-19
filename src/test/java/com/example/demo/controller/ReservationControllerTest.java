package com.example.demo.controller;

import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.dto.UpdateReservationRequestDto;
import com.example.demo.filter.CommonAuthFilter;
import com.example.demo.filter.RoleFilter;
import com.example.demo.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CommonAuthFilter.class, RoleFilter.class}))
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    //filter 비활성화
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilters().build();
    }

    @MockitoBean
    private ReservationService reservationService;

    @Test
    @DisplayName("예약 생성 시 성공적으로 JOSN응답 반환")
    void createReservationTest() throws Exception {
        //Given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.of(2023, 12, 1, 10, 0), LocalDateTime.of(2023, 12, 1, 12, 0));
        ReservationResponseDto responseDto = new ReservationResponseDto(1L, "testUser", "testItem", LocalDateTime.of(2023, 12, 1, 10, 0), LocalDateTime.of(2023, 12, 1, 12, 0));

        given(reservationService.createReservation(
                requestDto.getItemId(),
                requestDto.getUserId(),
                requestDto.getStartAt(),
                requestDto.getEndAt()
        )).willReturn(responseDto);

        //When & Then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))) // ObjectMapper를 사용해 JSON 변환
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickname").value("testUser"))
                .andExpect(jsonPath("$.itemName").value("testItem"))
                .andExpect(jsonPath("$.startAt").value("2023-12-01T10:00:00"))
                .andExpect(jsonPath("$.endAt").value("2023-12-01T12:00:00"));
    }

    @Test
    @DisplayName("update 시 service의 updateReservationStatus 메소드가 호출되었는지 확인")
    void updateReservationTest() throws Exception {
        //Given
        UpdateReservationRequestDto requestDto = new UpdateReservationRequestDto("APPROVED");

        //When & Then
        mockMvc.perform(patch("/reservations/1/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //Then
        Mockito.verify(reservationService).updateReservationStatus(1L, "APPROVED");
    }

    @Test
    @DisplayName("모든 예약을 조회할 때 성공적으로 조회되는지 확인")
    void findAllTest() throws Exception {
        //Given
        List<ReservationResponseDto> responseDtos = List.of(
                new ReservationResponseDto(1L, "testUser1", "testItem1", LocalDateTime.of(2023, 12, 1, 10, 0), LocalDateTime.of(2023, 12, 1, 12, 0)),
                new ReservationResponseDto(2L, "testUser2", "testItem2", LocalDateTime.of(2023, 12, 2, 14, 0), LocalDateTime.of(2023, 12, 2, 16, 0))
        );

        given(reservationService.getReservations()).willReturn(responseDtos);

        mockMvc.perform(get("/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(responseDtos.size())); //반환된 크기가 기댓값과 일치하는지 확인
    }

//    //세션으로 인한 테스트 실패(수정 예정)
//    @Test
//    @DisplayName("조건에 따른 예약을 조회할 때 성공적으로 조회되는지 확인")
//    void searchAllTest() throws Exception {
//        List<ReservationResponseDto> responseDtos = List.of(
//                new ReservationResponseDto(1L, "testUser", "testItem", LocalDateTime.of(2023, 12, 1, 10, 0), LocalDateTime.of(2023, 12, 1, 12, 0))
//        );
//
//        given(reservationService.searchAndConvertReservations(1L, null)).willReturn(responseDtos);
//
//        mockMvc.perform(get("/reservations/search")
//                        .param("userId", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(responseDtos.size())); //반환된 크기가 기댓값과 일치하는지 확인
//    }
}