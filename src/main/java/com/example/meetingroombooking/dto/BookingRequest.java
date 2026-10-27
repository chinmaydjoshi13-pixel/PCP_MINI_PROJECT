package com.example.meetingroombooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    @NotNull(message = "Attendee count is required")
    @Min(value = 1, message = "Attendee count must be at least 1")
    private Integer attendeeCount;

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    // Explicit Getters and Setters for JDK 25+ compilation compatibility
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public static BookingRequestBuilder builder() {
        return new BookingRequestBuilder();
    }

    public static class BookingRequestBuilder {
        private Long userId;
        private Long roomId;
        private String purpose;
        private Integer attendeeCount;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public BookingRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BookingRequestBuilder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public BookingRequestBuilder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public BookingRequestBuilder attendeeCount(Integer attendeeCount) {
            this.attendeeCount = attendeeCount;
            return this;
        }

        public BookingRequestBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public BookingRequestBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public BookingRequest build() {
            BookingRequest request = new BookingRequest();
            request.userId = this.userId;
            request.roomId = this.roomId;
            request.purpose = this.purpose;
            request.attendeeCount = this.attendeeCount;
            request.startTime = this.startTime;
            request.endTime = this.endTime;
            return request;
        }
    }
}
