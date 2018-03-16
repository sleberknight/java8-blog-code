package com.fortitudetec.java8.ex05.flatmap;

import java.util.ArrayList;
import java.util.List;

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
