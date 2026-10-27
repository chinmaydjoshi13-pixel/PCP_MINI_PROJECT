package com.example.meetingroombooking.service;

import com.example.meetingroombooking.dto.BookingRequest;
import com.example.meetingroombooking.dto.RoomAvailabilityRequest;
import com.example.meetingroombooking.entity.Booking;
import com.example.meetingroombooking.entity.MeetingRoom;
import com.example.meetingroombooking.entity.RoomFacility;
import com.example.meetingroombooking.entity.User;
import com.example.meetingroombooking.repository.BookingRepository;
import com.example.meetingroombooking.repository.MeetingRoomRepository;
import com.example.meetingroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          MeetingRoomRepository meetingRoomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    }

    /**
     * Creates a new booking after strictly validating time range, capacity, and overlapping conflicts.
     */
    public Booking createBooking(BookingRequest request) {
        validateTimeRange(request.getStartTime(), request.getEndTime());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + request.getUserId()));

        MeetingRoom room = meetingRoomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting room not found with id: " + request.getRoomId()));

        validateCapacity(request.getAttendeeCount(), room);
        validateNoOverlap(room.getId(), request.getStartTime(), request.getEndTime());

        Booking booking = Booking.builder()
                .user(user)
                .room(room)
                .purpose(request.getPurpose())
                .attendeeCount(request.getAttendeeCount())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status("CONFIRMED")
                .build();

        return bookingRepository.save(booking);
    }

    /**
     * Cancels an existing booking by setting its status to "CANCELLED".
     */
    public Booking cancelBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking with ID " + bookingId + " is already cancelled");
        }
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    /**
     * Retrieves booking details by ID.
     */
    @Transactional(readOnly = true)
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found with id: " + bookingId));
    }

    /**
     * Retrieves all bookings for a given user.
     */
    @Transactional(readOnly = true)
    public List<Booking> getUserBookings(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
        return bookingRepository.findByUserId(userId);
    }

    /**
     * Retrieves the schedule (confirmed bookings) for a given meeting room.
     */
    @Transactional(readOnly = true)
    public List<Booking> getRoomSchedule(Long roomId) {
        if (!meetingRoomRepository.existsById(roomId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting room not found with id: " + roomId);
        }
        return bookingRepository.findByRoomIdAndStatus(roomId, "CONFIRMED");
    }

    /**
     * Finds all available meeting rooms matching capacity, facility, and time criteria with no overlaps.
     */
    @Transactional(readOnly = true)
    public List<MeetingRoom> findAvailableRooms(RoomAvailabilityRequest request) {
        validateTimeRange(request.getStartTime(), request.getEndTime());

        List<MeetingRoom> candidateRooms;
        if (request.getRequiredCapacity() != null && request.getRequiredCapacity() > 0) {
            candidateRooms = meetingRoomRepository.findByCapacityGreaterThanEqual(request.getRequiredCapacity());
        } else {
            candidateRooms = meetingRoomRepository.findAll();
        }

        return candidateRooms.stream()
                .filter(room -> hasAllRequiredFacilities(room, request.getRequiredFacilities()))
                .filter(room -> isRoomAvailable(room.getId(), request.getStartTime(), request.getEndTime()))
                .collect(Collectors.toList());
    }

    // --- Private Helper Validations ---

    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null || !endTime.isAfter(startTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be strictly after start time");
        }
    }

    private void validateCapacity(Integer attendeeCount, MeetingRoom room) {
        if (attendeeCount == null || attendeeCount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendee count must be at least 1");
        }
        if (attendeeCount > room.getCapacity()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Attendee count (%d) exceeds room capacity (%d)", attendeeCount, room.getCapacity())
            );
        }
    }

    private void validateNoOverlap(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(roomId, startTime, endTime);
        if (!overlapping.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room is already booked for this time slot");
        }
    }

    private boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return bookingRepository.findOverlappingBookings(roomId, startTime, endTime).isEmpty();
    }

    private boolean hasAllRequiredFacilities(MeetingRoom room, List<String> requiredFacilities) {
        if (requiredFacilities == null || requiredFacilities.isEmpty()) {
            return true;
        }

        if (room == null || room.getFacilities() == null || room.getFacilities().isEmpty()) {
            return false;
        }

        Set<String> roomFacilityNames = room.getFacilities().stream()
                .filter(f -> f != null && f.getFacilityName() != null)
                .map(RoomFacility::getFacilityName)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return requiredFacilities.stream()
                .filter(rf -> rf != null && !rf.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .allMatch(roomFacilityNames::contains);
    }
}
