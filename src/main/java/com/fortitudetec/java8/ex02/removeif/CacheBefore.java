package com.fortitudetec.java8.ex02.removeif;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheBefore implements Cache {

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

    // Original code
    @Override
    public void deleteFromCache(Set<String> deleteKeys) {
        Iterator<Map.Entry<String, Object>> iterator = dataCache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (deleteKeys.contains(entry.getKey())) {
                iterator.remove();
            }
        }

    }

    // The above implementation could be simpler using a plain "foreach" loop...
/*
    @Override
    public void deleteFromCache(Set<String> deleteKeys) {
        for (String deleteKey : deleteKeys) {
            dataCache.remove(deleteKey);
        }
    }
*/

}
