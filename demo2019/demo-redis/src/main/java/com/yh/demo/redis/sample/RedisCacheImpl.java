package com.yh.demo.redis.sample;

import com.yh.demo.redis.util.ArrayUtils;
import com.yh.demo.redis.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisCacheImpl implements RedisCache {

    private String prefix = "";

    public RedisCacheImpl() {

    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private int globalDbIndex = 0;

    @Override
    public Long deleteCached(final Object dbIndex, final byte[] key) throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                return connection.del(key);
            }
        });
    }

    @Override
    public Long deleteCached(final Object dbIndex, String key) throws Exception {
        return deleteCached(dbIndex, key.getBytes());
    }

    @Override
    public String updateCached(final Object dbIndex, final byte[] orgKey, final byte[] value, final Long expireSec) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return (String) redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(final RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                connection.set(key, value);
                if (expireSec != null) {
                    connection.expire(key, expireSec);
                }
                return new String(key);
            }
        });
    }

    @Override
    public Object updateCached(final Object dbIndex, String key, Object value, Long expireSec) throws Exception {
        return updateCached(dbIndex, key.getBytes(), SerializeUtil.serialize(value), expireSec);
    }

    @Override
    public Boolean updateCachedNX(final Object dbIndex, final byte[] orgKey, final byte[] value, final Long expireSec) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(final RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Boolean b = connection.setNX(key, value);
                if (b && expireSec != null) {
                    connection.expire(key, expireSec);
                }
                return b;
            }
        });
    }

    @Override
    public Boolean updateCachedNX(final Object dbIndex, String key, Object value, Long expireSec) throws Exception {
        return updateCachedNX(dbIndex, key.getBytes(), SerializeUtil.serialize(value), expireSec);
    }

    @Override
    public Object getCached(final Object dbIndex, final byte[] orgKey) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                byte[] bs = connection.get(key);
                return SerializeUtil.unserialize(bs);
            }
        });
    }

    @Override
    public Object getCached(final Object dbIndex, String key) throws Exception {
        return getCached(dbIndex, key.getBytes());
    }

    @Override
    public Long ttl(final Object dbIndex, final byte[] key) throws Exception {
        return (Long) redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(final RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Long t = connection.ttl(key);
                return t;
            }
        });
    }

    @Override
    public Long ttl(final Object dbIndex, final String key) throws Exception {
        return ttl(dbIndex, key.getBytes());
    }

    @Override
    public Set getKeys(final Object dbIndex, final byte[] orgKey) throws Exception {
        final byte[] keys = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<Set>() {
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Set<byte[]> setByte = connection.keys(keys);
                if (setByte == null || setByte.size() < 1) {
                    return null;
                }
                Set set = new HashSet();
                for (byte[] key : setByte) {
                    set.add(new String(key));
                }
                return set;
            }
        });
    }

    @Override
    public Set getKeys(final Object dbIndex, String keys) throws Exception {
        return getKeys(dbIndex, keys.getBytes());
    }

    @Override
    public Set getHashKeys(final Object dbIndex, final byte[] orgKey) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return (Set) redisTemplate.execute(new RedisCallback<Set>() {
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Set<byte[]> hKeys = connection.hKeys(key);
                if (hKeys == null || hKeys.size() > 1) {
                    return null;
                }
                Set set = new HashSet();
                for (byte[] bs : hKeys) {
                    set.add(SerializeUtil.unserialize(bs));
                }
                return set;
            }
        });
    }

    @Override
    public Set getHashKeys(final Object dbIndex, String key) throws Exception {
        return getHashKeys(dbIndex, key.getBytes());
    }

    @Override
    public Boolean updateHashCached(final Object dbIndex, final byte[] orgKey, final byte[] field, final byte[] value) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Boolean hSet = connection.hSet(key, field, value);
                return hSet;
            }
        });
    }

    @Override
    public Boolean updateHashCached(final Object dbIndex, String key, String field, Object value) throws Exception {
        return updateHashCached(dbIndex, key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
    }

    @Override
    public Object getHashCached(final Object dbIndex, final byte[] orgKey, final byte[] field) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                byte[] hGet = connection.hGet(key, field);
                return SerializeUtil.unserialize(hGet);
            }
        });
    }

    @Override
    public Object getHashCached(final Object dbIndex, String key, String field) throws Exception {
        return getHashCached(dbIndex, key.getBytes(), field.getBytes());
    }

    @Override
    public Long deleteHashCached(final Object dbIndex, final byte[] orgKey, final byte[] field) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Long hDel = connection.hDel(key, field);
                return hDel;
            }
        });
    }

    @Override
    public Long deleteHashCached(final Object dbIndex, String key, String field) throws Exception {
        return deleteHashCached(dbIndex, key.getBytes(), field.getBytes());
    }

    @Override
    public Long getHashSize(final Object dbIndex, final byte[] orgKey) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Long len = connection.hLen(key);
                return len;
            }
        });
    }

    @Override
    public Long getHashSize(final Object dbIndex, String key) throws Exception {
        return getHashSize(dbIndex, key.getBytes());
    }

    @Override
    public List getHashValues(final Object dbIndex, final byte[] orgKey) throws Exception {
        final byte[] key = ArrayUtils.addAll(prefix.getBytes(), orgKey);
        return redisTemplate.execute(new RedisCallback<List>() {
            public List doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                List<byte[]> hVals = connection.hVals(key);

                if (hVals == null || hVals.size() < 1) {
                    return null;
                }
                List list = new ArrayList();

                for (byte[] bs : hVals) {
                    list.add(SerializeUtil.unserialize(bs));
                }
                return list;
            }
        });
    }

    @Override
    public List getHashValues(final Object dbIndex, String key) throws Exception {
        return getHashValues(dbIndex, key.getBytes());
    }

    @Override
    public Long getDBSize(final Object dbIndex) throws Exception {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                Long len = connection.dbSize();
                return len;
            }
        });
    }

    @Override
    public void clearDB(final Object dbIndex) throws Exception {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                if (dbIndex != null) {
                    connection.select((Integer) dbIndex);
                } else {
                    connection.select(globalDbIndex);
                }
                connection.flushDb();
                return null;
            }
        });
    }

    @Override
    public void clearDB() throws Exception {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
    }

    @Override
    public void clearDB2() throws Exception {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(globalDbIndex);
                Set<byte[]> keys = connection.keys((prefix + "cache_session_*").getBytes());

                Map<byte[], byte[]> cacheMap = new HashMap<byte[], byte[]>();
                for (byte[] key : keys) {
                    byte[] value = connection.get(key);
                    cacheMap.put(key, value);
                }
                connection.flushAll();

                for (Map.Entry<byte[], byte[]> entry : cacheMap.entrySet()) {
                    connection.setEx(entry.getKey(), 86400, entry.getValue());
                }
                return null;
            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int getGlobalDbIndex() {
        return globalDbIndex;
    }

    public void setGlobalDbIndex(int globalDbIndex) {
        this.globalDbIndex = globalDbIndex;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
