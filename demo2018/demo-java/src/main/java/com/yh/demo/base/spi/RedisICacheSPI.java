package com.yh.demo.base.spi;

/**
 * @author yanghan
 * @date 2021/5/28
 */
public class RedisICacheSPI implements ICacheSPI {
    @Override
    public String getName() {
        return "缓存方式：Redis";
    }
}
