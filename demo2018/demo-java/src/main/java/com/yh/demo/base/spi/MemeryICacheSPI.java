package com.yh.demo.base.spi;

/**
 * @author yanghan
 * @date 2021/5/28
 */
public class MemeryICacheSPI implements ICacheSPI {
    @Override
    public String getName() {
        return "缓存类型：内存";
    }
}
