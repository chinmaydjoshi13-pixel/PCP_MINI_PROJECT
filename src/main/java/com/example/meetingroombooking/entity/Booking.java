package com.example.meetingroombooking.entity;

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
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom room;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private Integer attendeeCount;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String status; // "CONFIRMED", "CANCELLED"

    // Explicit Getters and Setters for JDK 25+ compilation compatibility
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeetingRoom getRoom() {
        return room;
    }

    public void setRoom(MeetingRoom room) {
        this.room = room;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(Integer attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static BookingBuilder builder() {
        return new BookingBuilder();
    }

    public static class BookingBuilder {
        private Long id;
        private User user;
        private MeetingRoom room;
        private String purpose;
        private Integer attendeeCount;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String status;

        public BookingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingBuilder user(User user) {
            this.user = user;
            return this;
        }

        public BookingBuilder room(MeetingRoom room) {
            this.room = room;
            return this;
        }

        public BookingBuilder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public BookingBuilder attendeeCount(Integer attendeeCount) {
            this.attendeeCount = attendeeCount;
            return this;
        }

        public BookingBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public BookingBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public BookingBuilder status(String status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            Booking booking = new Booking();
            booking.id = this.id;
            booking.user = this.user;
            booking.room = this.room;
            booking.purpose = this.purpose;
            booking.attendeeCount = this.attendeeCount;
            booking.startTime = this.startTime;
            booking.endTime = this.endTime;
            booking.status = this.status;
            return booking;
        }
    }
}
