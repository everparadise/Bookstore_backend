package com.example.main.util;


import cn.hutool.core.util.StrUtil;
import com.example.main.Constants.RedisConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClient {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    private final AtomicLong lastCheckTime = new AtomicLong(0);
    private final AtomicBoolean connected = new AtomicBoolean(true);

    /**
     * 包装后的删除缓存的函数，处理了可能的异常
     */
    public Boolean safeDelete(String key) {
        try {

            boolean connected = isConnected();
            if (!connected) return null;
            System.out.println("redis safeDelete: " + key);
            return stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    public Long safeDeleteByKeyPrefix(String prefix) {
        try {
            boolean connected = isConnected();
            if (!connected) return null;

            Set<String> keys = stringRedisTemplate.keys(prefix);
            if (keys != null) {
                return stringRedisTemplate.delete(keys);
            } else return 0L;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    /**
     * （1）向Redis存入一段view数据。可设定过期时间
     * key约定形如 cache:view:{uid}&2024-07-01&2024-08-01
     * value类型为 String，代表CacheViewDto(List<DayWithEvents>)
     * （2）向Redis存入单个event的详细数据。可设定过期时间
     * key约定形如 cache:event:{uuid}
     * value类型为 String，代表单个EventDto对象
     */
    public void setRedis(String key, Object value, Long time, TimeUnit unit){
        try {
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), time, unit);
            System.out.println("setRedis: " + key + " " + value + " " + time + " " + unit);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * 从Redis查询EventDto（针对缓存穿透进行了优化）
     */
    public <R, ID> Optional<R> queryRedis(String keyPrefix, ID id, Class<R> type, Function<ID, Optional<R>> dbFallback, Long time, TimeUnit unit)  {
        try {
            System.out.println("query redis: " + keyPrefix + ":" + id + " " + type + " " + time + " " + unit);
            boolean connected = isConnected();
            System.out.println("connected: " + connected);
            String key = keyPrefix + id;
            // 1.从redis查询事项缓存
            String json = null;
            if (connected) {
                try {
                    json = stringRedisTemplate.opsForValue().get(key);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
            // 2.判断是否存在
            if (StrUtil.isNotBlank(json)) {
                System.out.println("redis hit: " + key + " " + json);
                // 3.存在，直接返回
                return Optional.of(objectMapper.readValue(json, type));
            }
            // 判断命中的是否是空值
            if (json != null) {
                // 返回一个错误信息
                return Optional.empty();
            }

            // entries为空，表示redis miss
            // 4.不存在，根据id查询数据库
            Optional<R> opR = dbFallback.apply(id);
            System.out.println("dbFallback: " + opR);
            // 5.不存在，返回错误
            if (opR.isEmpty()) {
                // 将空值写入redis
                if (connected) {
                    stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                }
                // 返回错误信息
                return Optional.empty();
            }
            // 6.存在，写入redis
            R r = opR.get();
            if (connected) {
                try {
                    this.setRedis(key, r, time, unit);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
            return Optional.of(r);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Optional.empty();
        }
    }

//    public boolean isConnected() {
//        System.out.println("check connection");
//        try {
//            if (stringRedisTemplate.getConnectionFactory() != null) {
//                RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
//                System.out.println("connection: " + connection);
//                return !connection.isClosed();
//            }
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//        }
//        return false;
//    }

    public boolean isConnected() {
        System.out.println("check connection: " + connected.get() + " " + (System.currentTimeMillis() - lastCheckTime.get()) + " " + RedisConstants.COOL_DOWN_TIME);
        if(!connected.get() && System.currentTimeMillis() - lastCheckTime.get() > RedisConstants.COOL_DOWN_TIME) {
            System.out.println("time to retry");
            if(stringRedisTemplate.getConnectionFactory() != null){
                try{
                    RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
                    if(connection.ping() != null){
                        connected.set(true);
                        lastCheckTime.set(System.currentTimeMillis());
                        System.out.println("retry success");
                        return true;
                    }
                }catch(Exception e){
                    lastCheckTime.set(System.currentTimeMillis());
                    return false;
                }
            }
            else {
                lastCheckTime.set(System.currentTimeMillis());
                return false;
            }
        }
        else if(!connected.get()){
            System.out.println("not time to retry");
            return false;
        }
        try {
            if (stringRedisTemplate.getConnectionFactory() != null) {
                RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
                try {
                    // 执行PING命令检查连接是否真正活跃
                    if(connection.ping() != null){
                        lastCheckTime.set(System.currentTimeMillis());
                        System.out.println("ping success");
                        return true;
                    }
                    else {
                        lastCheckTime.set(System.currentTimeMillis());
                        System.out.println("set false1");
                        connected.set(false);
                    }
                } catch (Exception e){
                    // 关闭这个测试连接
                    // connection.close();
                    lastCheckTime.set(System.currentTimeMillis());
                    System.out.println("set false2");
                    connected.set(false);
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return false;
    }
}
