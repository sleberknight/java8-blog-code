package com.fortitudetec.java8.ex01.query;

public class QueryBuilderAfterTest extends AbstractQueryBuilderTest {

    @Override
    public QueryBuilder getQueryBuilder() {
        return new QueryBuilderAfter();
    }

}
