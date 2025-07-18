package dev.honokai.data_integrator_backend.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
