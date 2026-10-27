package com.example.meetingroombooking.service;

import com.example.meetingroombooking.entity.MeetingRoom;
import com.example.meetingroombooking.entity.RoomFacility;
import com.example.meetingroombooking.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public RoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public MeetingRoom createRoom(MeetingRoom room) {
        if (room.getFacilities() != null) {
            room.getFacilities().forEach(facility -> facility.setRoom(room));
        }
        return meetingRoomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public List<MeetingRoom> getAllRooms() {
        return meetingRoomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MeetingRoom getRoomById(Long id) {
        return meetingRoomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting room not found with id: " + id));
    }

    public MeetingRoom updateRoom(Long id, MeetingRoom updatedRoom) {
        MeetingRoom existing = getRoomById(id);

        existing.setName(updatedRoom.getName());
        existing.setCapacity(updatedRoom.getCapacity());
        existing.setLocation(updatedRoom.getLocation());

        if (updatedRoom.getFacilities() != null) {
            existing.getFacilities().clear();
            for (RoomFacility facility : updatedRoom.getFacilities()) {
                facility.setRoom(existing);
                existing.getFacilities().add(facility);
            }
        }

        return meetingRoomRepository.save(existing);
    }

    public void deleteRoom(Long id) {
        MeetingRoom room = getRoomById(id);
        meetingRoomRepository.delete(room);
    }
}
