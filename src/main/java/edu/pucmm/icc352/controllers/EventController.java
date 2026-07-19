package edu.pucmm.icc352.controllers;

import edu.pucmm.icc352.models.Event;
import edu.pucmm.icc352.services.EventService;
import edu.pucmm.icc352.dto.EventDTO;
import io.javalin.apibuilder.ApiBuilder;
import java.util.Map;

public class EventController {

    private static final EventService svc = new EventService();

    @SuppressWarnings("unchecked")
    public static void register() {

        ApiBuilder.get("/api/events", ctx -> {
            ctx.json(svc.findPublished());
        });

        ApiBuilder.get("/api/events/mine", ctx -> {
            Long userId = ctx.sessionAttribute("userId");
            if (userId == null) {
                ctx.status(401).json(Map.of("error", "No autenticado."));
                return;
            }
            ctx.json(svc.findByOrganizer(userId));
        });

        ApiBuilder.get("/api/events/{id}", ctx -> {
            long id = Long.parseLong(ctx.pathParam("id"));
            ctx.json(svc.findById(id));
        });

        ApiBuilder.post("/api/events", ctx -> {
            Long userId = ctx.sessionAttribute("userId");
            String role = ctx.sessionAttribute("role");
            if (userId == null) {
                ctx.status(401).json(Map.of("error", "No autenticado."));
                return;
            }
            if ("PARTICIPANT".equals(role)) {
                ctx.status(403).json(Map.of("error", "Sin permiso."));
                return;
            }
            EventDTO dto = ctx.bodyAsClass(EventDTO.class);
            Event event = svc.create(
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getDateTime(),
                    dto.getEndDateTime(),
                    dto.getLocation(),
                    dto.getMaxCapacity() == null ? 0 : dto.getMaxCapacity(),
                    userId
            );
            ctx.status(201).json(event);
        });

        ApiBuilder.put("/api/events/{id}", ctx -> {
            Long userId = ctx.sessionAttribute("userId");
            String role = ctx.sessionAttribute("role");
            if (userId == null) {
                ctx.status(401).json(Map.of("error", "No autenticado."));
                return;
            }
            if ("PARTICIPANT".equals(role)) {
                ctx.status(403).json(Map.of("error", "Sin permiso."));
                return;
            }
            long eventId = Long.parseLong(ctx.pathParam("id"));
            EventDTO dto = ctx.bodyAsClass(EventDTO.class);
            ctx.json(svc.update(
                    eventId, userId,
                    dto.getTitle(),
                    dto.getDescription(),
                    dto.getDateTime(),
                    dto.getEndDateTime(),
                    dto.getLocation(),
                    dto.getMaxCapacity() == null ? 0 : dto.getMaxCapacity()
            ));
        });

        ApiBuilder.patch("/api/events/{id}/publish", ctx -> {
            Long userId = ctx.sessionAttribute("userId");
            String role = ctx.sessionAttribute("role");
            if (userId == null) {
                ctx.status(401).json(Map.of("error", "No autenticado."));
                return;
            }
            if ("PARTICIPANT".equals(role)) {
                ctx.status(403).json(Map.of("error", "Sin permiso."));
                return;
            }
            long eventId = Long.parseLong(ctx.pathParam("id"));
            Map<String, Object> b = (Map<String, Object>) ctx.bodyAsClass(Map.class);
            boolean publish = (Boolean) b.get("publish");
            ctx.json(svc.setPublished(eventId, userId, publish));
        });

        ApiBuilder.patch("/api/events/{id}/cancel", ctx -> {
            Long userId = ctx.sessionAttribute("userId");
            String role = ctx.sessionAttribute("role");
            if (userId == null) {
                ctx.status(401).json(Map.of("error", "No autenticado."));
                return;
            }
            if ("PARTICIPANT".equals(role)) {
                ctx.status(403).json(Map.of("error", "Sin permiso."));
                return;
            }
            long eventId = Long.parseLong(ctx.pathParam("id"));
            ctx.json(svc.cancel(eventId, userId)); //si
        });
    }
}