package com.fortitudetec.java8.ex04.nested;

import java.util.Optional;

/**
 * Java 8 implementation to find C2 (or any type that extends C) in the A data structure.
 * This implementation assumes that the argument and nested collections are not null.
 */
public class SimpleC2FinderAfter {

    /**
     * Finds only the first C2.
     */
    public Optional<C2> findFirstC2(A a) {
        return a.getBs().stream()
                .flatMap(b -> b.getCs().stream())
                .filter(C2.class::isInstance)
                .map(C2.class::cast)
                .findFirst();
    }

    /**
     * Finds first item of type T in the nested collection of C's.
     */
    public <T extends C> Optional<T> findFirst(A a, Class<T> clazz) {
        return a.getBs().stream()
                .flatMap(b -> b.getCs().stream())
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
    }

}
