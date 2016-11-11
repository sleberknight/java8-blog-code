package com.fortitudetec.java8.ex04.nested;

import java.util.Optional;

/**
 * Java 8 implementation to find C2 (or any type that extends C) in the A data structure.
 * This implementation assumes that the argument and nested collections are not null.
 * <p>
 * Accompanies blog 'Towards More Functional Java - Digging into Nested Data Structures'. URLs:
 * <p>
 * http://www.fortitudetec.com/blogs/2016/11/11/towards-more-functional-java-dig-data-structures
 * <p>
 * http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_digging
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
