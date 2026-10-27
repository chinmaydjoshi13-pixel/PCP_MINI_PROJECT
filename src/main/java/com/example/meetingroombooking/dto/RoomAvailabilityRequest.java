package com.example.meetingroombooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomAvailabilityRequest {

    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    private Integer requiredCapacity;

    private List<String> requiredFacilities;

    // Explicit Getters and Setters for JDK 25+ compilation compatibility
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

    public Integer getRequiredCapacity() {
        return requiredCapacity;
    }

    public void setRequiredCapacity(Integer requiredCapacity) {
        this.requiredCapacity = requiredCapacity;
    }

    public List<String> getRequiredFacilities() {
        return requiredFacilities;
    }

    public void setRequiredFacilities(List<String> requiredFacilities) {
        this.requiredFacilities = requiredFacilities;
    }

    public static RoomAvailabilityRequestBuilder builder() {
        return new RoomAvailabilityRequestBuilder();
    }

    public static class RoomAvailabilityRequestBuilder {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer requiredCapacity;
        private List<String> requiredFacilities;

        public RoomAvailabilityRequestBuilder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public RoomAvailabilityRequestBuilder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public RoomAvailabilityRequestBuilder requiredCapacity(Integer requiredCapacity) {
            this.requiredCapacity = requiredCapacity;
            return this;
        }

        public RoomAvailabilityRequestBuilder requiredFacilities(List<String> requiredFacilities) {
            this.requiredFacilities = requiredFacilities;
            return this;
        }

        public RoomAvailabilityRequest build() {
            RoomAvailabilityRequest request = new RoomAvailabilityRequest();
            request.startTime = this.startTime;
            request.endTime = this.endTime;
            request.requiredCapacity = this.requiredCapacity;
            request.requiredFacilities = this.requiredFacilities;
            return request;
        }
    }
}
