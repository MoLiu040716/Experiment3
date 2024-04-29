package ynu.edu.cache;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CachingService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final long CACHE_EXPIRY = 60L;

    public String getCachedResult(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void cacheResult(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(CACHE_EXPIRY));
    }
}
