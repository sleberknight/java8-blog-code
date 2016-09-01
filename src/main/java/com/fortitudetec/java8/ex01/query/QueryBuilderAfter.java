package com.fortitudetec.java8.ex01.query;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Java 8 implementation of QueryBuilder using stream API.
 */
public class QueryBuilderAfter implements QueryBuilder {

    private static final String DEFAULT_QUERY = "*";
    private static final String AND = " AND ";
    private static final String COLON = ":";

    @Override
    public String buildQueryString(Map<String, String> parameters) {
        if (parameters.isEmpty()) {
            return DEFAULT_QUERY;
        }

        return parameters.entrySet().stream()
                .map(entry -> String.join(COLON, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(AND));
    }

}
