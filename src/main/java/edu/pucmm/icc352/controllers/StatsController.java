package edu.pucmm.icc352.controllers;

import edu.pucmm.icc352.services.StatsService;
import edu.pucmm.icc352.services.StatsBroadcaster;
import io.javalin.apibuilder.ApiBuilder;
import java.util.Map;

public class StatsController {

    private static final StatsService svc = new StatsService();

    public static void register() {

        ApiBuilder.get("/api/stats/{eventId}", ctx -> {
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
            long eventId = Long.parseLong(ctx.pathParam("eventId"));
            ctx.json(svc.getEventStats(eventId));
        });

        ApiBuilder.ws("/api/stats/ws/{eventId}", ws -> {
            ws.onConnect(ctx -> {
                Long userId = ctx.sessionAttribute("userId");
                String role = ctx.sessionAttribute("role");
                if (userId == null || "PARTICIPANT".equals(role)) {
                    ctx.closeSession(1008, "Sin permiso");
                    return;
                }
                long eventId = Long.parseLong(ctx.pathParam("eventId"));
                StatsBroadcaster.addConnection(eventId, ctx);
            });
            ws.onClose(ctx -> {
                long eventId = Long.parseLong(ctx.pathParam("eventId"));
                StatsBroadcaster.removeConnection(eventId, ctx);
            });
        });
    }
}