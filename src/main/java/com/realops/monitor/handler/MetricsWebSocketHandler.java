package com.realops.monitor.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import tools.jackson.databind.ObjectMapper;

@Component
public class MetricsWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public MetricsWebSocketHandler() {
        // 2초마다 모든 연결된 클라이언트에게 메트릭 전송
        scheduler.scheduleAtFixedRate(this::broadcastMetrics, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("WebSocket Connected: " + session.getId());
        
        // 연결 즉시 첫 데이터 전송
        sendMetrics(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket Disconnected: " + session.getId());
    }

    private void broadcastMetrics() {
        Map<String, Object> metrics = generateMetrics();
        
        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    String json = objectMapper.writeValueAsString(metrics);
                    session.sendMessage(new TextMessage(json));
                }
            } catch (IOException e) {
                System.err.println("Error sending metrics: " + e.getMessage());
            }
        });
    }

    private void sendMetrics(WebSocketSession session) {
        try {
            Map<String, Object> metrics = generateMetrics();
            String json = objectMapper.writeValueAsString(metrics);
            session.sendMessage(new TextMessage(json));
        } catch (IOException e) {
            System.err.println("Error sending initial metrics: " + e.getMessage());
        }
    }

    private Map<String, Object> generateMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("cpu", 30 + random.nextInt(40));
        metrics.put("memory", 50 + random.nextInt(30));
        metrics.put("disk", 70 + random.nextInt(15));
        metrics.put("network", 100 + random.nextInt(50));
        metrics.put("timestamp", System.currentTimeMillis());
        return metrics;
    }
}