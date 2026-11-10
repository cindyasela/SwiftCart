package com.tek.swiftcarts;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService {

    private final StringRedisTemplate redisTemplate;

    public RedisTestService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String testRedis() {

        redisTemplate.opsForValue()
                .set("message", "Hello from Spring Boot!");

        return redisTemplate.opsForValue()
                .get("message");
    }
}