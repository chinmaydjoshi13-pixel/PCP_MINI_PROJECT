package com.example.meetingroombooking.repository;

import com.example.meetingroombooking.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

    // Spring Data derived query method
    List<MeetingRoom> findByCapacityGreaterThanEqual(Integer requiredCapacity);

    // Custom JPQL query method
    @Query("SELECT r FROM MeetingRoom r WHERE r.capacity >= :requiredCapacity")
    List<MeetingRoom> findRoomsWithCapacityGreaterOrEqualTo(@Param("requiredCapacity") Integer requiredCapacity);
}
