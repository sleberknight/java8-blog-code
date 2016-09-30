package com.fortitudetec.java8.ex03.randomports;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A generalized mechanism for finding the first of something, given a generator,
 * condition to satisfy, and max number of attempts.
 */
public class GenericFinder {

    public <T> Optional<T> findFirst(long maxAttempts,
                                     Supplier<T> generator,
                                     Predicate<T> condition) {
        return Stream.generate(generator)
                .limit(maxAttempts)
                .filter(condition)
                .findFirst();
    }

}
