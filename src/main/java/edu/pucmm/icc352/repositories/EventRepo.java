package edu.pucmm.icc352.repositories;

import edu.pucmm.icc352.models.Event;
import edu.pucmm.icc352.models.EventStatus;
import java.util.List;

public class EventRepo extends BaseRepo<Event> {

    public EventRepo() { super(Event.class); }

    public List<Event> findPublished() {
        try (var s = openSession()) {
            return s.createQuery(
                            "FROM Event WHERE status = :s ORDER BY dateTime ASC", Event.class)
                    .setParameter("s", EventStatus.PUBLISHED)
                    .list();
        }
    }

    public List<Event> findByOrganizer(Long organizerId) {
        try (var s = openSession()) {
            return s.createQuery(
                            "FROM Event WHERE organizer.id = :id ORDER BY dateTime DESC", Event.class)
                    .setParameter("id", organizerId)
                    .list();
        }
    }

    public long countRegistrations(Long eventId) {
        try (var s = openSession()) {
            return s.createQuery(
                            "SELECT COUNT(r) FROM Registration r WHERE r.event.id = :id", Long.class)
                    .setParameter("id", eventId)
                    .uniqueResult();
        }
    }

    public long countAttendance(Long eventId) {
        try (var s = openSession()) {
            return s.createQuery(
                            "SELECT COUNT(a) FROM Attendance a WHERE a.registration.event.id = :id", Long.class)
                    .setParameter("id", eventId)
                    .uniqueResult();
        }
    }
}