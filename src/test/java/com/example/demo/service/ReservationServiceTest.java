package com.example.demo.service;

import com.example.demo.constants.ReservationStatus;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.entity.Item;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ReservationQueryRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  //TestDB를 H2 DB가 아닌 MySQL사용(자동 교체 방지)
@TestPropertySource(locations = "classpath:application-test.yml") //Test 환경설정 파일 명시
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentalLogService rentalLogService;

    @Mock
    private ReservationQueryRepository reservationQueryRepository;

    @Test
    @DisplayName("모든 예약 조회")
    void getReservationsTest() {
        // Given
        Item mockItem = new Item();
        User mockUser = new User();
        Reservation mockReservation = new Reservation(mockItem, mockUser, ReservationStatus.PENDING.toString(), LocalDateTime.of(2023, 12, 1, 10, 0), LocalDateTime.of(2023, 12, 1, 12, 0));
        given(reservationRepository.findAllWithUserAndItem()).willReturn(List.of(mockReservation));

        // When
        List<ReservationResponseDto> responses = reservationService.getReservations();

        // Then
        assertEquals(1, responses.size());
        verify(reservationRepository).findAllWithUserAndItem();
    }

    @Test
    @DisplayName("예약 상태 업데이트")
    void updateReservationStatus_Success() {
        // Given
        Item mockItem = new Item();
        User mockUser = new User();
        Long reservationId = 1L;
        String newStatus = "APPROVED";
        Reservation mockReservation = new Reservation(mockItem, mockUser, ReservationStatus.PENDING.toString(), LocalDateTime.of(2023, 12, 1, 10, 0), LocalDateTime.of(2023, 12, 1, 12, 0));

        given(reservationRepository.findByIdOrElseThrow(reservationId)).willReturn(mockReservation);

        // When
        reservationService.updateReservationStatus(reservationId, newStatus);

        // Then
        assertEquals(newStatus, mockReservation.getStatus());
        verify(reservationRepository).findByIdOrElseThrow(reservationId);
    }

}