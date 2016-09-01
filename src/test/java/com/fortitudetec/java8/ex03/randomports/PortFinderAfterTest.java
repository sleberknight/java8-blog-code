package com.fortitudetec.java8.ex03.randomports;

import org.junit.Before;
import org.junit.Test;

import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fortitudetec.java8.ex03.randomports.PortFinderAfter.MAX_PORT;
import static com.fortitudetec.java8.ex03.randomports.PortFinderAfter.MAX_PORT_CHECK_ATTEMPTS;
import static com.fortitudetec.java8.ex03.randomports.PortFinderAfter.MIN_PORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PortFinderAfterTest {

    private PortFinderAfter portFinder;
    private PortChecker portChecker;

    @Before
    public void setUp() {
        portChecker = mock(PortChecker.class);
        portFinder = new PortFinderAfter(portChecker, new Random());
    }

    @Test
    public void testFindFreePort_WhenNoOpenPortsAreFound() {
        when(portChecker.isAvailable(anyInt())).thenReturn(false);

        OptionalInt port = portFinder.findFreePort();
        assertThat(port.isPresent()).isFalse();
    }

    @Test
    public void testFindFreePort_WhenOpenPortFoundAfterSeveralAttempts() {
        when(portChecker.isAvailable(anyInt()))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);

        int port = portFinder.findFreePort().orElse(-1);
        assertThat(port).isBetween(MIN_PORT, MAX_PORT);
    }

    @Test
    public void testFindFreePort_WhenOpenPortFoundOnLastAttempt() {
        AtomicInteger counter = new AtomicInteger(0);
        int bound = MAX_PORT_CHECK_ATTEMPTS - 1;
        when(portChecker.isAvailable(anyInt()))
                .thenAnswer(invocation -> counter.incrementAndGet() > bound);

        int port = portFinder.findFreePort().orElse(-1);
        assertThat(port).isBetween(MIN_PORT, MAX_PORT);
    }

}