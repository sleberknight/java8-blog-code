package com.fortitudetec.java8.ex01.query;

public class QueryBuilderBeforeTest extends AbstractQueryBuilderTest {

    @Override
    public QueryBuilder getQueryBuilder() {
        return new QueryBuilderBefore();
    }

}
