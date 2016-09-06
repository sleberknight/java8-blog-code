package com.fortitudetec.java8.ex04.nested;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C2FinderAfterTest {

    private C2FinderAfter c2Finder;

    @Before
    public void setUp() {
        c2Finder = new C2FinderAfter();
    }

    @Test
    public void testFindFirstC2_WhenNullA() {
        assertThat(c2Finder.findFirstC2(null).orElse(null)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenEmptyBs() {
        A a = A.builder()
                .bs(Lists.newArrayList())
                .build();
        assertThat(a.getBs()).isEmpty();
        assertThat(c2Finder.findFirstC2(a).orElse(null)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenNullBs() {
        A a = A.builder().build();
        a.setBs(null);
        assertThat(a.getBs()).isNull();
        assertThat(c2Finder.findFirstC2(a).orElse(null)).isNull();
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
        assertThat(c2Finder.findFirstC2(a).orElse(null)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenCsAreNull() {
        B b = B.builder().build();
        b.setCs(null);
        assertThat(b.getCs()).isNull();
        A a = A.builder().b(b).build();
        assertThat(c2Finder.findFirstC2(a).orElse(null)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenOneC2() {
        C2 theC2 = new C2("c2-1");
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
        assertThat(c2Finder.findFirstC2(a).orElse(null)).isNotNull().isSameAs(theC2);
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
        assertThat(c2Finder.findFirstC2(a).orElse(null)).isNotNull().isSameAs(firstC2);
    }

    @Test
    public void testFindFirstC1() {
        C1 firstC1 = new C1("c1-1");
        A a = A.builder()
                .b(B.builder()
                        .c(firstC1)
                        .c(new C2("c2-1"))
                        .c(new C3("c3-1"))
                        .c(new C3("c3-2"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirstC1(a).orElse(null)).isNotNull().isSameAs(firstC1);
    }

    @Test
    public void testFindFirstC3() {
        C3 firstC3 = new C3("c3-1");
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .c(new C2("c2-1"))
                        .c(firstC3)
                        .c(new C3("c3-2"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirstC3(a).orElse(null)).isNotNull().isSameAs(firstC3);
    }

    @Test
    public void testFindFirstCOfAnyType() {
        C firstC = new C1("c1-1");
        A a = A.builder()
                .b(B.builder()
                        .c(firstC)
                        .c(new C2("c2-1"))
                        .c(new C3("c3-1"))
                        .c(new C3("c3-2"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirst(a, C.class).orElse(null)).isNotNull().isSameAs(firstC);
    }

    @Test
    public void testFindAll_C3() {
        C3 firstC3 = new C3("c3-1");
        C3 secondC3 = new C3("c3-2");
        C3 thirdC3 = new C3("c3-3");
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .c(new C2("c2-1"))
                        .c(firstC3)
                        .c(secondC3)
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .c(thirdC3)
                        .build())
                .build();
        assertThat(c2Finder.findAll(a, C3.class))
                .containsExactly(firstC3, secondC3, thirdC3);
    }

}