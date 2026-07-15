package edu.pucmm.icc352.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "registrations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "event_id"})
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false, unique = true)
    private String qrToken;

    @JsonIgnore
    @OneToOne(mappedBy = "registration", fetch = FetchType.EAGER)
    private Attendance attendance;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt = LocalDateTime.now();

    public Registration() {}

    public Registration(Long id, User user, Event event, String qrToken, Attendance attendance, LocalDateTime registeredAt) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.qrToken = qrToken;
        this.attendance = attendance;
        this.registeredAt = registeredAt;
    }

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}