package com.realops.monitor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MetricsController {
    
    private Random random = new Random();
    
    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // CPU 메트릭
        metrics.put("cpu", 30 + random.nextInt(40));
        
        // Memory 메트릭
        metrics.put("memory", 50 + random.nextInt(30));
        
        // Disk 메트릭
        metrics.put("disk", 70 + random.nextInt(15));
        
        // Network 메트릭
        metrics.put("network", 100 + random.nextInt(50));
        
        // 타임스탬프
        metrics.put("timestamp", System.currentTimeMillis());
        
        return metrics;
    }
    
    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "online");
        status.put("message", "System operational");
        return status;
    }
}
