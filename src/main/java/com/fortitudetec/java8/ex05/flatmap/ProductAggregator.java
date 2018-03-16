package com.fortitudetec.java8.ex05.flatmap;

import java.util.List;

public interface ProductAggregator {

    List<Product> aggregateByCategory(List<Location> locations, String category);

    List<Product> aggregateByCode(List<Location> locations, String code);

}
