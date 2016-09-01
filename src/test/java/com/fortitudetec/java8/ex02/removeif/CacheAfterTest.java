package com.fortitudetec.java8.ex02.removeif;

public class CacheAfterTest extends AbstractCacheTest {

    @Override
    public Cache getCache() {
        return new CacheAfter();
    }
}
