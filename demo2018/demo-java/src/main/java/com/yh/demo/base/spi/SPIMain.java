package com.yh.demo.base.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author yanghan
 * @date 2021/5/28
 */
public class SPIMain {
    public static void main(String[] args) {
        ServiceLoader<ICacheSPI> serviceLoader = ServiceLoader.load(ICacheSPI.class);
        // 用迭代器遍历，便于理解。foreach本质也是迭代器的封装
        Iterator<ICacheSPI> iterable = serviceLoader.iterator();
        while (iterable.hasNext()){
            ICacheSPI iCacheSPI = iterable.next();
            System.out.println(iCacheSPI.getName());
        }
    }
}
