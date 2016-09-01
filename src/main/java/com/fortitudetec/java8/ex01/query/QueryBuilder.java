package com.fortitudetec.java8.ex01.query;

import java.util.Map;

/**
 * QueryBuilder and its implementations accompany this blog:
 * <a href="http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_using">Towards more functional Java using Streams and Lambdas</a>
 */
public interface QueryBuilder {

    String buildQueryString(Map<String, String> parameters);

}
