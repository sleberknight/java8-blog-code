package com.fortitudetec.java8.ex05.flatmap;

public class AggregateProductsBeforeHybridTest extends AbstractAggregateProductsTest {

    @Override
    ProductAggregator getAggregator() {
        return new AggregateProductsBeforeHybrid();
    }

}
