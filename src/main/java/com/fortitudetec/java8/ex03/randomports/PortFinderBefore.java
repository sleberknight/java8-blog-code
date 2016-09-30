package com.fortitudetec.java8.ex03.randomports;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Pre-Java 8 implementation to find free ports.
 */
public class PortFinderBefore {

    private static final Logger LOG = LoggerFactory.getLogger(PortFinderBefore.class);

    // Only use ports in dynamic/private range (see www.iana.org/assignments/port-numbers)
    @VisibleForTesting static final int MIN_PORT = 49152;
    @VisibleForTesting static final int MAX_PORT = 65535;
    private static final int PORTS_IN_RANGE = MAX_PORT - MIN_PORT + 1;
    @VisibleForTesting static final int MAX_PORT_CHECK_ATTEMPTS = 100;

    private final Random random;
    private final PortChecker portChecker;

    public PortFinderBefore() {
        this(new PortChecker(), new Random());
    }

    @VisibleForTesting
    PortFinderBefore(PortChecker portChecker, Random random) {
        this.portChecker = portChecker;
        this.random = random;
    }

    /**
     * Typical pre-Java 8 implementation to find free port (this was the original code I found).
     *
     * @return the free port number, or null if no open port was found after {@link #MAX_PORT_CHECK_ATTEMPTS} attempts
     */
    public Integer findFreePort() {
        int assignedPort = -1;
        int count = 1;
        while (count <= MAX_PORT_CHECK_ATTEMPTS) {
            int checkPort = MIN_PORT + random.nextInt(PORTS_IN_RANGE);
            if (portChecker.isAvailable(checkPort)) {
                assignedPort = checkPort;
                LOG.trace("Found free port {} on attempt {}", assignedPort, count);
                break;
            }
            count++;
        }
        return assignedPort == -1 ? null : assignedPort;
    }

    /**
     * A slightly improved pre-Java 8 version of the above findFreePort method. It removes the
     * mutable assignedPort variable and the ternary at the end of the method.
     *
     * @return the free port number, or null if no open port was found after {@link #MAX_PORT_CHECK_ATTEMPTS} attempts
     */
    public Integer findFreePortSlightlyBetter() {
        int count = 1;
        while (count <= MAX_PORT_CHECK_ATTEMPTS) {
            int checkPort = MIN_PORT + random.nextInt(PORTS_IN_RANGE);
            if (portChecker.isAvailable(checkPort)) {
                LOG.trace("Found free port {} on attempt {}", checkPort, count);
                return checkPort;
            }
            count++;
        }
        return null;
    }

}
