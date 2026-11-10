package com.tek.swiftcarts.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisConnectionController {

    private final StringRedisTemplate redisTemplate;

    public RedisConnectionController(
            StringRedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/test")
    public String testRedis() {

        redisTemplate.opsForValue()
                .set("swiftcarts:message", "Redis Connected Successfully");

        return redisTemplate.opsForValue()
                .get("swiftcarts:message");
    }
}