package com.example.demo.constants;

public enum ReservationStatus {
    PENDING,
    APPROVED,
    CANCELED,
    EXPIRED;

    // 상태 변경 검증 메서드
    public void updateStatus(ReservationStatus currentStatus) {
        switch (this) {
            case APPROVED:
                if (currentStatus != PENDING) {
                    throw new IllegalArgumentException("PENDING 상태만 APPROVED로 변경 가능합니다.");
                }
                break;

            case CANCELED:
                if (currentStatus == EXPIRED) {
                    throw new IllegalArgumentException("EXPIRED 상태인 예약은 취소할 수 없습니다.");
                }
                break;

            case EXPIRED:
                if (currentStatus != PENDING) {
                    throw new IllegalArgumentException("PENDING 상태만 EXPIRED로 변경 가능합니다.");
                }
                break;

            default:
                throw new IllegalArgumentException("올바르지 않은 상태 변경: " + this);
        }
    }
}
