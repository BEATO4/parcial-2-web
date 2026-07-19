package edu.pucmm.icc352.dto;

import java.time.LocalDateTime;

public class EventDTO {
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private LocalDateTime endDateTime;
    private String location;
    private Integer maxCapacity;
    
    // Additional fields like "publish" might come from the client depending on the UI
    // The current EventController gets these from the map: title, description, dateTime, endDateTime, location, maxCapacity.

    public EventDTO() {
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }
}
