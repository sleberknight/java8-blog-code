package com.fortitudetec.java8.ex05.flatmap;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Shows functional style aggregation, adding a more generic method that accepts any {@link Predicate}
 * to make the function more flexible (since you supply any predicate on Products).
 */
public class AggregateProductsAfterGeneric implements ProductAggregatorExtended {

    public List<Product> aggregateByCategory(List<Location> locations, String category) {
        return aggregate(locations, product -> category.equals(product.getCategory()));
    }

    public List<Product> aggregateByCode(List<Location> locations, String code) {
        return aggregate(locations, product -> code.equals(product.getCode()));
    }

    public List<Product> aggregate(List<Location> locations, Predicate<Product> predicate) {
        return locations.stream()
                .flatMap(location -> location.getProducts().stream())
                .filter(predicate)
                .collect(toList());
    }

}
