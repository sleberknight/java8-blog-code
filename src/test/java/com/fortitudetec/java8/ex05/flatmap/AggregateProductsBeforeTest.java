package com.fortitudetec.java8.ex05.flatmap;

public class AggregateProductsBeforeTest extends AbstractAggregateProductsTest {

    @Override
    ProductAggregator getAggregator() {
        return new AggregateProductsBefore();
    }

}
