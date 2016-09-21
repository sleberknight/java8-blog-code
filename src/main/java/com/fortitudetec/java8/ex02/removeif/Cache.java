package com.fortitudetec.java8.ex02.removeif;

import java.util.Optional;
import java.util.Set;

/**
 * Cache and its implementations accompany this blog:
 * <a href="http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_using1">
 *     Towards More Functional Java using Lambdas as Predicates
 * </a>
 */
public interface Cache {

    long count();

    void put(String key, Object value);

    Optional<Object> get(String key);

    void deleteFromCache(Set<String> deleteKeys);

}
