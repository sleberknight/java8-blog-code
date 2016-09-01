package com.fortitudetec.java8.ex02.removeif;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCacheTest {

    private Cache cache;

    public abstract Cache getCache();

    @Before
    public void setUp() {
        cache = getCache();
    }

    @Test
    public void testDeleteFromCache_WhenEmptyDeleteKeys() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.deleteFromCache(newHashSet());
        assertThat(cache.count()).isEqualTo(3);
        assertThat(cache.get("a").orElse("")).isEqualTo(1);
        assertThat(cache.get("b").orElse("")).isEqualTo(2);
        assertThat(cache.get("c").orElse("")).isEqualTo(3);
    }

    @Test
    public void testDeleteFromCache_WhenDeleteKeysNotInCache() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.deleteFromCache(newHashSet("d", "e"));
        assertThat(cache.count()).isEqualTo(3);
        assertThat(cache.get("a").orElse("")).isEqualTo(1);
        assertThat(cache.get("b").orElse("")).isEqualTo(2);
        assertThat(cache.get("c").orElse("")).isEqualTo(3);
    }

    @Test
    public void testDeleteFromCache_WhenSingleDeleteKey() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.deleteFromCache(newHashSet("b"));
        assertThat(cache.count()).isEqualTo(2);
        assertThat(cache.get("a").orElse("")).isEqualTo(1);
        assertThat(cache.get("b").orElse("")).isEqualTo("");
        assertThat(cache.get("c").orElse("")).isEqualTo(3);
    }

    @Test
    public void testDeleteFromCache_WhenMultipleDeleteKeys() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.deleteFromCache(newHashSet("b", "a"));
        assertThat(cache.count()).isEqualTo(1);
        assertThat(cache.get("a").orElse("")).isEqualTo("");
        assertThat(cache.get("b").orElse("")).isEqualTo("");
        assertThat(cache.get("c").orElse("")).isEqualTo(3);
    }

    @Test
    public void testDeleteFromCache_WhenAllDeleteKeys() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.deleteFromCache(newHashSet("b", "c", "a"));
        assertThat(cache.count()).isZero();
        assertThat(cache.get("a").orElse("")).isEqualTo("");
        assertThat(cache.get("b").orElse("")).isEqualTo("");
        assertThat(cache.get("c").orElse("")).isEqualTo("");
    }

    @Test
    public void testDeleteFromCache_WhenMoreDeleteKeysThanKeysInCache() {
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);

        cache.deleteFromCache(newHashSet("a", "b", "c", "d", "e"));
        assertThat(cache.count()).isZero();
        assertThat(cache.get("a").orElse("")).isEqualTo("");
        assertThat(cache.get("b").orElse("")).isEqualTo("");
        assertThat(cache.get("c").orElse("")).isEqualTo("");
    }

}
