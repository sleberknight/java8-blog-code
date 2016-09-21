package com.fortitudetec.java8.ex03.randomports;

import com.google.common.annotations.VisibleForTesting;

import java.util.OptionalInt;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * Java 8 implementation to find free ports. Demonstrates using Optional, Supplier, and infinite sequence
 * generation using a limit and filter.
 */
public class PortFinderAfter {

    @VisibleForTesting static final int MIN_PORT = 1024;
    @VisibleForTesting static final int MAX_PORT = 65535;
    private static final int PORTS_IN_RANGE = MAX_PORT - MIN_PORT + 1;
    @VisibleForTesting static final int MAX_PORT_CHECK_ATTEMPTS = 100;

    private final Random random;
    private final PortChecker portChecker;

    public PortFinderAfter() {
        this(new PortChecker(), new Random());
    }

    @VisibleForTesting
    PortFinderAfter(PortChecker portChecker, Random random) {
        this.portChecker = portChecker;
        this.random = random;
    }

    /**
     * @return an optional containing the free port number, or an empty optional if no open port was found
     * after {{@link #MAX_PORT_CHECK_ATTEMPTS}} attempts
     */
    public OptionalInt findFreePort() {
        IntSupplier randomPorts = () -> MIN_PORT + random.nextInt(PORTS_IN_RANGE);
        return IntStream.generate(randomPorts)
                .limit(MAX_PORT_CHECK_ATTEMPTS)
                .filter(portChecker::isAvailable)
                .findFirst();
    }
}
