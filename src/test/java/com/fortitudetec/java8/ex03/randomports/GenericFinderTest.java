package com.fortitudetec.java8.ex03.randomports;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GenericFinderTest {

    private GenericFinder finder;

    @Before
    public void setUp() {
        finder = new GenericFinder();
    }

    @Test
    public void testFindFirstNumberOverTenMillion() {
        Random rand = Mockito.mock(Random.class);
        when(rand.nextInt())
                .thenReturn(5_000_000)
                .thenReturn(400_000)
                .thenReturn(10_000_000)
                .thenReturn(75_000)
                .thenReturn(10_000_001);

        Integer first = finder.findFirst(10,
                rand::nextInt,
                value -> value > 10_000_000)
                .orElse(null);
        assertThat(first).isEqualTo(10_000_001);
    }

    @Test
    public void testFindFirstNumberOverTenMillion_WhenNeverFound() {
        Random rand = Mockito.mock(Random.class);
        when(rand.nextInt())
                .thenReturn(5_000_000)
                .thenReturn(400_000)
                .thenReturn(10_000_000)
                .thenReturn(75_000)
                .thenReturn(7_000_000);

        Optional<Integer> first = finder.findFirst(5,
                rand::nextInt,
                value -> value > 10_000_000);
        assertThat(first.isPresent()).isFalse();
    }

    @Test
    public void testFindFirstNumberOverTwoBillion_PrintingOutput_NotARealTest() {
        Random rand = new Random();
        IntStream.rangeClosed(1, 50).forEach(ignoredIndex -> {
            Optional<Integer> firstOrEmpty = finder.findFirst(10, rand::nextInt, value -> value > 2_000_000_000);
            System.out.println("firstOrEmpty = " + firstOrEmpty);
        });
    }

    @Test
    public void testFindFirst_PrintingValueOnlyIfOverTwoBillion_NotARealTest() {
        Random rand = new Random();
        finder.findFirst(10, rand::nextInt, value -> value > 2_000_000_000)
                .ifPresent(System.out::println);
    }

}