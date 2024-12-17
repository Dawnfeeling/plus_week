package com.example.demo.repository;

import com.example.demo.entity.QItem;
import com.example.demo.entity.QReservation;
import com.example.demo.entity.QUser;
import com.example.demo.entity.Reservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Reservation> searchReservations(Long userId, Long itemId) {
        QReservation reservation = QReservation.reservation;
        QUser user = QUser.user;
        QItem item = QItem.item;

        // BooleanBuilder로 동적 조건 생성(null이 아니면 조건 추가)
        BooleanBuilder builder = new BooleanBuilder();

        if (userId != null) {
            builder.and(reservation.user.id.eq(userId));
        }
        if (itemId != null) {
            builder.and(reservation.item.id.eq(itemId));
        }

        return queryFactory
                .selectFrom(reservation)
                .join(reservation.user, user).fetchJoin()
                .join(reservation.item, item).fetchJoin()
                .where(builder)
                .fetch();
    }
}
