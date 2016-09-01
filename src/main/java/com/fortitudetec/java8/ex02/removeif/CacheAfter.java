package com.fortitudetec.java8.ex02.removeif;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Java 8 implementation of Cache.
 */
public class CacheAfter implements Cache {

    private final ConcurrentMap<String, Object> dataCache = new ConcurrentHashMap<>();

    @Override
    public long count() {
        return dataCache.size();
    }

    @Override
    public void put(String key, Object value) {
        dataCache.put(key, value);
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(dataCache.get(key));
    }

    // First try - use an anonymous inner class to implement the Predicate
/*
    @Override
    public void deleteFromCache(Set<String> deleteKeys) {
        dataCache.entrySet().removeIf(new Predicate<Map.Entry<String, Object>>() {
            @Override
            public boolean test(Map.Entry<String, Object> entry) {
                return deleteKeys.contains(entry.getKey());
            }
        });
    }
*/

    // Second try - use a lambda with an explicit parameter type
/*
    @Override
    public void deleteFromCache(Set<String> deleteKeys) {
        dataCache.entrySet().removeIf((Map.Entry<String, Object> entry) -> deleteKeys.contains(entry.getKey()));
    }
*/

    // Third time's a charm - use a lambda with inferred parameter type
    @Override
    public void deleteFromCache(Set<String> deleteKeys) {
        dataCache.entrySet().removeIf(entry -> deleteKeys.contains(entry.getKey()));
    }

}
