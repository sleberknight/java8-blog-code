package com.fortitudetec.java8.ex04.nested;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class C2FinderBeforeTest {

    private C2FinderBefore c2Finder;

    @Before
    public void setUp() {
        c2Finder = new C2FinderBefore();
    }

    @Test
    public void testFindFirstC2_WhenNullA() {
        assertThat(c2Finder.findFirstC2(null)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenEmptyBs() {
        A a = A.builder()
                .bs(Lists.newArrayList())
                .build();
        assertThat(a.getBs()).isEmpty();
        assertThat(c2Finder.findFirstC2(a)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenNullBs() {
        A a = A.builder().build();
        a.setBs(null);
        assertThat(a.getBs()).isNull();
        assertThat(c2Finder.findFirstC2(a)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenNoC2s() {
        A a = A.builder()
                .b(B.builder()
                        .c(new C1("c1-1"))
                        .build())
                .b(B.builder()
                        .c(new C1("c1-2"))
                        .c(new C3("c3-2"))
                        .c(new C3("c3-2"))
                        .build())
                .build();
        assertThat(c2Finder.findFirstC2(a)).isNull();
    }

    @Test
    public void testFindFirstC2_WhenCsAreNull() {
        B b = B.builder().build();
        b.setCs(null);
        assertThat(b.getCs()).isNull();
        A a = A.builder().b(b).build();
        assertThat(c2Finder.findFirstC2(a)).isNull();
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
        assertThat(c2Finder.findFirstC2(a)).isNotNull().isSameAs(theC2);
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
        assertThat(c2Finder.findFirstC2(a)).isNotNull().isSameAs(firstC2);
    }

}