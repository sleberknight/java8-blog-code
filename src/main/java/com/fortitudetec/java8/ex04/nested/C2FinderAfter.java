package com.fortitudetec.java8.ex04.nested;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 8 implementation to find C2 (or any type that extends C) in the A data structure.
 * Shows several iterations of findFirstC2 method, ending up at the generic {@link #findFirst(A, Class)}
 * method. This implementation assumes that the collections could be null and defensively checks these cases.
 * <p>
 * Accompanies blog 'Towards More Functional Java - Digging into Nested Data Structures'. URLs:
 * <p>
 * http://www.fortitudetec.com/blogs/2016/11/11/towards-more-functional-java-dig-data-structures
 * <p>
 * http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_digging
 */
@SuppressWarnings("unused")
public class C2FinderAfter {

    /**
     * Iteration #0:
     * initial implementation;
     * flatMap has entire chain of methods;
     * filter uses an instanceof
     */
    public Optional<C2> findFirstC2_Iteration_0(A a) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(b -> Optional.ofNullable(b.getCs()).orElseGet(Lists::newArrayList).stream())
                .filter(o -> o instanceof C2)
                .map(C2.class::cast)
                .findFirst();
    }

    /**
     * Iteration #1:
     * extract toStreamOfC method used in flatMap
     */
    public Optional<C2> findFirstC2_Iteration_1(A a) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfC)
                .filter(o -> o instanceof C2)
                .map(C2.class::cast)
                .findFirst();
    }

    /**
     * Iteration #2:
     * extract isC2 method and use method reference in filter
     */
    public Optional<C2> findFirstC2_Iteration_2(A a) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfC)
                .filter(this::isC2)
                .map(C2.class::cast)
                .findFirst();
    }

    private boolean isC2(C obj) {
        return C2.class.isAssignableFrom(obj.getClass());
    }

    /**
     * Iteration #3:
     * refactor isC2 method as more generic is(obj, clazz) method
     */
    public Optional<C2> findFirstC2_Iteration_3(A a) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfC)
                .filter(o -> is(o, C2.class))
                .map(C2.class::cast)
                .findFirst();
    }

    private boolean is(Object obj, Class<?> clazz) {
        return clazz.isAssignableFrom(obj.getClass());
    }

    /**
     * Iteration #4:
     * replace is(obj, clazz) method with {@link Class#isInstance(Object)} method reference
     */
    public Optional<C2> findFirstC2_Iteration_4(A a) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfC)
                .filter(C2.class::isInstance)
                .map(C2.class::cast)
                .findFirst();
    }

    /**
     * Iteration #5:
     * completely generic findFirst method taking the class T to find, where T is a subclass of C
     */
    public <T extends C> Optional<T> findFirst(A a, Class<T> clazz) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfC)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
    }

    private Stream<? extends C> toStreamOfC(B b) {
        return Optional.ofNullable(b.getCs())
                .orElseGet(Lists::newArrayList)
                .stream();
    }

    public Optional<C1> findFirstC1(A a) {
        return findFirst(a, C1.class);
    }

    public Optional<C2> findFirstC2(A a) {
        return findFirst(a, C2.class);
    }

    public Optional<C3> findFirstC3(A a) {
        return findFirst(a, C3.class);
    }

    /**
     * Can take the above pipeline and extract method to get all Ts in a stream, then collect to a list.
     * Could easily make the generic findFirst method call toStreamOfC(A) and then simply call findFirst()
     */
    public <T extends C> List<T> findAll(A a, Class<T> clazz) {
        return toStreamOfC(a, clazz).collect(Collectors.toList());
    }

    /**
     * Version of {@link #findFirst(A, Class)} that re-uses the extracted {@link #toStreamOfC(A, Class)} method,
     * showing how the stream API supports composition much better.
     */
    public <T extends C> Optional<T> findFirst2(A a, Class<T> clazz) {
        return toStreamOfC(a, clazz).findFirst();
    }

    private <T extends C> Stream<T> toStreamOfC(A a, Class<T> clazz) {
        return Optional.ofNullable(a)
                .map(A::getBs)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfC)
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

}
