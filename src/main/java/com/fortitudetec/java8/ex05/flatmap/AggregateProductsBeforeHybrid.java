package com.fortitudetec.java8.ex05.flatmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows imperative style aggregation using nested loops (via forEach) and a conditional.
 * The forEach is intended for operations that perform side-effects and so the code in this
 * class is less than ideal since we are mutating (side-effect) an ArrayList inside a forEach
 * instead of using functional constructs like map, flatMap, and filter.
 */
public class AggregateProductsBeforeHybrid implements ProductAggregator {

    public List<Product> aggregateByCategory(List<Location> locations, String category) {
        List<Product> categoryProducts = new ArrayList<>();

        locations.forEach(location ->
                location.getProducts().forEach(product -> {
                    if (category.equals(product.getCategory())) {
                        categoryProducts.add(product);
                    }
                }));

        return categoryProducts;
    }

    public List<Product> aggregateByCode(List<Location> locations, String code) {
        List<Product> codeProducts = new ArrayList<>();

        locations.forEach(location ->
                location.getProducts().forEach(product -> {
                    if (code.equals(product.getCode())) {
                        codeProducts.add(product);
                    }
                }));

        return codeProducts;
    }

}
