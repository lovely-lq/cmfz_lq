package com.baizhi.lq.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MybatisCache implements Cache {
    //创建一个id属性
    private final String id;

    public MybatisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    //添加缓存
    @Override
    public void putObject(Object key, Object value) {
        // 通过工具类获取RedisTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebAware.getByName("redisTemplate");
        // 通过RedisTemplate对象操作Redis
        // 注意: 存储时使用hash类型存储数据 方便数据更改时删除当前namespace的所有数据
        redisTemplate.opsForHash().put(this.id, key.toString(), value);
    }

    //
    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebAware.getByName("redisTemplate");
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());

        return o;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        // 通过工具类获取RedisTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebAware.getByName("redisTemplate");
        redisTemplate.delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
