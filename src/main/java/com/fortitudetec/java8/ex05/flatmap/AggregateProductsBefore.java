package com.fortitudetec.java8.ex05.flatmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows imperative style aggregation using nested loops and a conditional.
 */
public class AggregateProductsBefore implements ProductAggregator {

    @Override
    public List<Product> aggregateByCategory(List<Location> locations, String category) {
        List<Product> categoryProducts = new ArrayList<>();

        for (Location location : locations) {
            for (Product product : location.getProducts()) {
                if (category.equals(product.getCategory())) {
                    categoryProducts.add(product);
                }
            }
        }

        return categoryProducts;
    }

    @Override
    public List<Product> aggregateByCode(List<Location> locations, String code) {
        List<Product> codeProducts = new ArrayList<>();

        for (Location location : locations) {
            for (Product product : location.getProducts()) {
                if (code.equals(product.getCode())) {
                    codeProducts.add(product);
                }
            }
        }

        return codeProducts;
    }

}
