package edu.pucmm.icc352.services;

import io.javalin.websocket.WsContext;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatsBroadcaster {
    
    // Maps Event ID to a set of active WebSocket contexts
    private static final Map<Long, Set<WsContext>> eventConnections = new ConcurrentHashMap<>();
    private static final StatsService statsService = new StatsService();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void addConnection(Long eventId, WsContext ctx) {
        eventConnections.computeIfAbsent(eventId, k -> ConcurrentHashMap.newKeySet()).add(ctx);
        // Send initial stats immediately
        sendStats(eventId, ctx);
    }

    public static void removeConnection(Long eventId, WsContext ctx) {
        Set<WsContext> contexts = eventConnections.get(eventId);
        if (contexts != null) {
            contexts.remove(ctx);
        }
    }

    public static void broadcast(Long eventId) {
        Set<WsContext> contexts = eventConnections.get(eventId);
        if (contexts != null && !contexts.isEmpty()) {
            Map<String, Object> stats = statsService.getEventStats(eventId);
            for (WsContext ctx : contexts) {
                if (ctx.session.isOpen()) {
                    ctx.send(stats);
                }
            }
        }
    }

    private static void sendStats(Long eventId, WsContext ctx) {
        if (ctx.session.isOpen()) {
            ctx.send(statsService.getEventStats(eventId));
        }
    }
}
