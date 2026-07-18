package edu.pucmm.icc352.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column
    private LocalDateTime endDateTime;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(nullable = false)
    private int maxCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EventStatus status = EventStatus.DRAFT;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Event(Long id, String title, String description, LocalDateTime dateTime, LocalDateTime endDateTime, String location, int maxCapacity, EventStatus status, User organizer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.status = status;
        this.organizer = organizer;
        this.createdAt = createdAt;
    }

    public Event(String title, String description, LocalDateTime dateTime, LocalDateTime effectiveEnd, String location, int maxCapacity, User organizer) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.endDateTime = effectiveEnd;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.organizer = organizer;
        this.status = EventStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

    public Event(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}