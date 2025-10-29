package cln.swiggy.restaurant.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper mapper;

    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if(o == null) return null;
            return mapper.readValue(o.toString(),entityClass);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public <T> T get(String key, TypeReference<T> typeReference){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if(o == null) return null;
            return mapper.readValue(o.toString(), typeReference);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void set(String key, Object o ,Long ttl){
        try {
            redisTemplate.opsForValue().set(key,mapper.writeValueAsString(o),ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
