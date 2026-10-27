package com.example.meetingroombooking.controller;

import com.example.meetingroombooking.dto.RoomAvailabilityRequest;
import com.example.meetingroombooking.entity.Booking;
import com.example.meetingroombooking.entity.MeetingRoom;
import com.example.meetingroombooking.service.BookingService;
import com.example.meetingroombooking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final BookingService bookingService;

    public RoomController(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<MeetingRoom> createRoom(@Valid @RequestBody MeetingRoom room) {
        MeetingRoom createdRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping
    public ResponseEntity<List<MeetingRoom>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping("/availability")
    public ResponseEntity<List<MeetingRoom>> searchAvailableRooms(@Valid @RequestBody RoomAvailabilityRequest request) {
        return ResponseEntity.ok(bookingService.findAvailableRooms(request));
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<Booking>> getRoomSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getRoomSchedule(id));
    }
}
