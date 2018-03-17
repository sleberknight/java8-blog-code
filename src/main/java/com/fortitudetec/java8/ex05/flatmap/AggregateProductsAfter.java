package com.fortitudetec.java8.ex05.flatmap;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Shows functional style aggregation using flatMap (instead of nested loops) and filter.
 */
public class AggregateProductsAfter implements ProductAggregator {

    public List<Product> aggregateByCategory(List<Location> locations, String category) {
        return locations.stream()
                .flatMap(location -> location.getProducts().stream())
                .filter(product -> category.equals(product.getCategory()))
                .collect(toList());
    }

    public List<Product> aggregateByCode(List<Location> locations, String code) {
        return locations.stream()
                .flatMap(location -> location.getProducts().stream())
                .filter(product -> code.equals(product.getCode()))
                .collect(toList());
    }

}
