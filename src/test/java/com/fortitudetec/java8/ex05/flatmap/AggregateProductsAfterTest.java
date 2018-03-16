package com.fortitudetec.java8.ex05.flatmap;

public class AggregateProductsAfterTest extends AbstractAggregateProductsTest {

    @Override
    ProductAggregator getAggregator() {
        return new AggregateProductsAfter();
    }

}
