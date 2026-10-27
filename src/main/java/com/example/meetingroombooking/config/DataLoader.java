package com.example.meetingroombooking.config;

import com.example.meetingroombooking.entity.MeetingRoom;
import com.example.meetingroombooking.entity.RoomFacility;
import com.example.meetingroombooking.entity.User;
import com.example.meetingroombooking.repository.BookingRepository;
import com.example.meetingroombooking.repository.MeetingRoomRepository;
import com.example.meetingroombooking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final UserRepository userRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final BookingRepository bookingRepository;

    public DataLoader(UserRepository userRepository,
                      MeetingRoomRepository meetingRoomRepository,
                      BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.meetingRoomRepository = meetingRoomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        log.info("--- Initializing Sample Data for Meeting Room Booking System ---");

        // Seed Users
        User admin = User.builder()
                .name("Alex Mercer")
                .email("admin@roomsphere.io")
                .role("Admin")
                .build();

        User employee = User.builder()
                .name("Sarah Connor")
                .email("sarah.connor@roomsphere.io")
                .role("Employee")
                .build();

        userRepository.saveAll(List.of(admin, employee));
        log.info("Initialized {} users", userRepository.count());

        // Seed Room 1: Boardroom
        MeetingRoom boardroom = MeetingRoom.builder()
                .name("Apex Boardroom")
                .capacity(18)
                .location("Tower 1 - Floor 12")
                .facilities(new ArrayList<>())
                .build();
        boardroom.getFacilities().add(RoomFacility.builder().facilityName("4K Video Conferencing").room(boardroom).build());
        boardroom.getFacilities().add(RoomFacility.builder().facilityName("Dual Projectors").room(boardroom).build());
        boardroom.getFacilities().add(RoomFacility.builder().facilityName("Digital Whiteboard").room(boardroom).build());

        // Seed Room 2: Huddle
        MeetingRoom huddle = MeetingRoom.builder()
                .name("Nexus Huddle")
                .capacity(6)
                .location("Tower 1 - Floor 3")
                .facilities(new ArrayList<>())
                .build();
        huddle.getFacilities().add(RoomFacility.builder().facilityName("Smart TV Display").room(huddle).build());
        huddle.getFacilities().add(RoomFacility.builder().facilityName("Whiteboard").room(huddle).build());

        // Seed Room 3: Innovation Lab
        MeetingRoom lab = MeetingRoom.builder()
                .name("Quantum Studio")
                .capacity(24)
                .location("Innovation Wing - Ground Floor")
                .facilities(new ArrayList<>())
                .build();
        lab.getFacilities().add(RoomFacility.builder().facilityName("Surround Sound").room(lab).build());
        lab.getFacilities().add(RoomFacility.builder().facilityName("4K Video Conferencing").room(lab).build());
        lab.getFacilities().add(RoomFacility.builder().facilityName("High-speed Fiber WiFi").room(lab).build());

        meetingRoomRepository.saveAll(List.of(boardroom, huddle, lab));
        log.info("Initialized {} meeting rooms with facilities", meetingRoomRepository.count());
    }
}
