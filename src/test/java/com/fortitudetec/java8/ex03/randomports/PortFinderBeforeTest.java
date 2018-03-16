package com.fortitudetec.java8.ex03.randomports;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fortitudetec.java8.ex03.randomports.PortFinderBefore.MAX_PORT;
import static com.fortitudetec.java8.ex03.randomports.PortFinderBefore.MAX_PORT_CHECK_ATTEMPTS;
import static com.fortitudetec.java8.ex03.randomports.PortFinderBefore.MIN_PORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PortFinderBeforeTest {

    private PortFinderBefore portFinder;
    private PortChecker portChecker;

    @Before
    public void setUp() {
        portChecker = mock(PortChecker.class);
        portFinder = new PortFinderBefore(portChecker, new Random());
    }

    @Test
    public void testFindFreePort_WhenNoOpenPortsAreFound() {
        when(portChecker.isAvailable(anyInt())).thenReturn(false);

        Integer port = portFinder.findFreePort();
        assertThat(port).isNull();
    }

    @Test
    public void testFindFreePort_WhenOpenPortFoundOnFirstTry() {
        when(portChecker.isAvailable(anyInt())).thenReturn(true);

        Integer port = portFinder.findFreePort();
        assertThat(port).isNotNull().isBetween(MIN_PORT, MAX_PORT);
    }

    @Test
    public void testFindFreePort_WhenOpenPortFoundAfterSeveralAttempts() {
        when(portChecker.isAvailable(anyInt()))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);

        Integer port = portFinder.findFreePort();
        assertThat(port).isNotNull().isBetween(MIN_PORT, MAX_PORT);
    }

    @Test
    public void testFindFreePort_WhenOpenPortFoundOnLastAttempt() {
        AtomicInteger counter = new AtomicInteger(0);
        int bound = MAX_PORT_CHECK_ATTEMPTS - 1;
        // See the same test in PortFinderAfterTest for a (much) cleaner way, using a lambda.
        // This test assumes we are still pre-Java 8, so uses an anonymous class for the Answer.
        when(portChecker.isAvailable(anyInt()))
                .thenAnswer(new Answer<Boolean>() {
                    @Override
                    public Boolean answer(InvocationOnMock invocation) throws Throwable {
                        return counter.incrementAndGet() > bound;
                    }
                });

        Integer port = portFinder.findFreePort();
        assertThat(port).isNotNull().isBetween(MIN_PORT, MAX_PORT);
    }

}