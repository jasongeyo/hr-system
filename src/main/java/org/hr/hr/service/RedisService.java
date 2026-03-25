package org.hr.hr.service;

import ch.qos.logback.core.util.TimeUtil;
import io.lettuce.core.api.sync.RedisTransactionalCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String , Object> redisTemplate;

    public Object get(String key) {

        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value, long timeout , TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }
}
