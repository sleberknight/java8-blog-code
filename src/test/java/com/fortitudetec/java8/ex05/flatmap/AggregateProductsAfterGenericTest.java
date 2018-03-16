package com.fortitudetec.java8.ex05.flatmap;

public class AggregateProductsAfterGenericTest extends AbstractAggregateProductsTest {

    @Override
    ProductAggregator getAggregator() {
        return new AggregateProductsAfterGeneric();
    }

}
