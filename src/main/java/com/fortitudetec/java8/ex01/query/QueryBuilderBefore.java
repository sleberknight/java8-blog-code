package com.fortitudetec.java8.ex01.query;

import java.util.Map;

/**
 * Pre-Java 8 implementation of QueryBuilder.
 */
public class QueryBuilderBefore implements QueryBuilder {

    private static final String DEFAULT_QUERY = "*";
    private static final String AND = " AND ";
    private static final String COLON = ":";

    public String buildQueryString(Map<String, String> parameters) {
        int count = 0;
        StringBuilder query = new StringBuilder();

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (count > 0) {
                query.append(AND);
            }
            query.append(entry.getKey());
            query.append(COLON);
            query.append(entry.getValue());
            count++;
        }

        if (parameters.size() == 0) {
            query.append(DEFAULT_QUERY);
        }

        return query.toString();
    }

}
