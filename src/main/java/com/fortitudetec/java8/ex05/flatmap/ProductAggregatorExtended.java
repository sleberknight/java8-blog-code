package com.fortitudetec.java8.ex05.flatmap;

import java.util.List;
import java.util.function.Predicate;

public interface ProductAggregatorExtended extends ProductAggregator {

    List<Product> aggregate(List<Location> locations, Predicate<Product> predicate);

}
