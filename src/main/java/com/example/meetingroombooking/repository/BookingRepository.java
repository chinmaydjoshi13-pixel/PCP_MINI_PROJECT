package com.example.meetingroombooking.repository;

import com.example.meetingroombooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomIdAndStatus(Long roomId, String status);

    List<Booking> findByUserId(Long userId);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
           "AND b.status = 'CONFIRMED' " +
           "AND b.startTime < :newEndTime " +
           "AND b.endTime > :newStartTime")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                         @Param("newStartTime") LocalDateTime newStartTime,
                                         @Param("newEndTime") LocalDateTime newEndTime);
}
