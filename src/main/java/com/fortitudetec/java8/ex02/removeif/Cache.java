package com.fortitudetec.java8.ex02.removeif;

import java.util.Optional;
import java.util.Set;

/**
 * Cache and its implementations accompany this blog:
 * TODO
 */
public interface Cache {

    long count();

    void put(String key, Object value);

    Optional<Object> get(String key);

    void deleteFromCache(Set<String> deleteKeys);

}
