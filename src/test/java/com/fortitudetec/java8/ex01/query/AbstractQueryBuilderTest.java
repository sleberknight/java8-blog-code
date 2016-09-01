package com.fortitudetec.java8.ex01.query;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractQueryBuilderTest {

    private QueryBuilder queryBuilder;

    public abstract QueryBuilder getQueryBuilder();

    @Before
    public void setUp() {
        queryBuilder = getQueryBuilder();
    }

    @Test
    public void testBuildQueryString_WhenNoParameters() {
        Map<String, String> parameters = new HashMap<>();
        assertThat(queryBuilder.buildQueryString(parameters)).isEqualTo("*");
    }

    @Test
    public void testBuildQueryString_WhenOneParameter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("docId", "123456");
        assertThat(queryBuilder.buildQueryString(parameters)).isEqualTo("docId:123456");
    }

    @Test
    public void testBuildQueryString_WhenTwoParameters() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("firstName", "Bob");
        parameters.put("lastName", "Sackamano");
        assertThat(queryBuilder.buildQueryString(parameters)).isEqualTo("firstName:Bob AND lastName:Sackamano");
    }

    @Test
    public void testBuildQueryString_WhenMultipleParameters() {
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("firstName", "Bob");
        parameters.put("lastName", "Sackamano");
        parameters.put("state", "NY");
        parameters.put("friend", "Kramer");
        assertThat(queryBuilder.buildQueryString(parameters))
                .isEqualTo("firstName:Bob AND lastName:Sackamano AND state:NY AND friend:Kramer");
    }

}