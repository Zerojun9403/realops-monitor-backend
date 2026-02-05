package com.realops.monitor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.realops.monitor.handler.MetricsWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MetricsWebSocketHandler metricsHandler;

    public WebSocketConfig(MetricsWebSocketHandler metricsHandler) {
        this.metricsHandler = metricsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(metricsHandler, "/ws/metrics")
                .setAllowedOrigins("http://localhost:3000");
    }
}

