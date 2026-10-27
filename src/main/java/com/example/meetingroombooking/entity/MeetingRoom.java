package com.example.meetingroombooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meeting_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @Builder.Default
    private List<RoomFacility> facilities = new ArrayList<>();

    // Explicit Getters and Setters for JDK 25+ compilation compatibility
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<RoomFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<RoomFacility> facilities) {
        this.facilities = facilities;
    }

    public static MeetingRoomBuilder builder() {
        return new MeetingRoomBuilder();
    }

    public static class MeetingRoomBuilder {
        private Long id;
        private String name;
        private Integer capacity;
        private String location;
        private List<RoomFacility> facilities = new ArrayList<>();

        public MeetingRoomBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MeetingRoomBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MeetingRoomBuilder capacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public MeetingRoomBuilder location(String location) {
            this.location = location;
            return this;
        }

        public MeetingRoomBuilder facilities(List<RoomFacility> facilities) {
            this.facilities = facilities != null ? facilities : new ArrayList<>();
            return this;
        }

        public MeetingRoom build() {
            MeetingRoom room = new MeetingRoom();
            room.id = this.id;
            room.name = this.name;
            room.capacity = this.capacity;
            room.location = this.location;
            room.facilities = this.facilities;
            return room;
        }
    }
}
