package com.fortitudetec.java8.ex02.removeif;

public class CacheBeforeTest extends AbstractCacheTest {

    @Override
    public Cache getCache() {
        return new CacheBefore();
    }
}
