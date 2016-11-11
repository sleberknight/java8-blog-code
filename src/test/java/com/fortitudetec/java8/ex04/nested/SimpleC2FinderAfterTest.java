package com.fortitudetec.java8.ex04.nested;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleC2FinderAfterTest {

    private SimpleC2FinderAfter c2Finder;

    @Before
    public void setUp() {
        c2Finder = new SimpleC2FinderAfter();
    }

    @Test
    public void testFindFirstC2_WhenNoC2s() {
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .c(new C3("c3-1"))
                        .c(new C3("c3-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirstC2(a)).isEmpty();
    }

    @Test
    public void testFindFirstC2_WhenOneC2() {
        C2 theC2 = new C2("c2-2");
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .c(theC2)
                        .c(new C3("c3-1"))
                        .c(new C3("c3-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirstC2(a)).contains(theC2);
    }

    @Test
    public void testFindFirstC2_WhenMultipleC2s() {
        C2 firstC2 = new C2("c2-1");
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .c(firstC2)
                        .c(new C2("c2-2"))
                        .c(new C3("c3-1"))
                        .c(new C3("c3-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirstC2(a)).contains(firstC2);
    }

    @Test
    public void testFindFirst_C3() {
        C3 firstC3 = new C3("c3-1");
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .c(new C2("c2-1"))
                        .c(new C2("c2-2"))
                        .c(firstC3)
                        .c(new C3("c3-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirst(a, C3.class)).contains(firstC3);
    }

}