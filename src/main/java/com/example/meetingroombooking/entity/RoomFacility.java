package com.example.meetingroombooking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "room_facilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RoomFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String facilityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private MeetingRoom room;

    // Explicit Getters and Setters for JDK 25+ compilation compatibility
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public MeetingRoom getRoom() {
        return room;
    }

    public void setRoom(MeetingRoom room) {
        this.room = room;
    }

    public static RoomFacilityBuilder builder() {
        return new RoomFacilityBuilder();
    }

    public static class RoomFacilityBuilder {
        private Long id;
        private String facilityName;
        private MeetingRoom room;

        public RoomFacilityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RoomFacilityBuilder facilityName(String facilityName) {
            this.facilityName = facilityName;
            return this;
        }

        public RoomFacilityBuilder room(MeetingRoom room) {
            this.room = room;
            return this;
        }

        public RoomFacility build() {
            RoomFacility facility = new RoomFacility();
            facility.id = this.id;
            facility.facilityName = this.facilityName;
            facility.room = this.room;
            return facility;
        }
    }
}
