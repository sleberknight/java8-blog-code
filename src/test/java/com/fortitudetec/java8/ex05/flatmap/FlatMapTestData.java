package com.fortitudetec.java8.ex05.flatmap;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@SuppressWarnings("WeakerAccess")
class FlatMapTestData {

    static final List<Location> LOCATIONS = sampleLocations();

    static List<Location> sampleLocations() {

        Location atlanta = newLocation("A", "Atlanta", "E", newArrayList(
                newProduct("A", "1000", "Soccer ball", "Sports", "Soccer", 25),
                newProduct("A", "1001", "Baseball glove", "Sports", "Baseball", 15),
                newProduct("A", "2000", "Atlanta Falcons Jersey", "Clothing", "Jerseys", 30),
                newProduct("A", "3000", "Men's Jeans", "Clothing", "Pants", 15)
        ));

        Location boston = newLocation("B", "Boston", "E", newArrayList(
                newProduct("B", "1000", "Soccer ball", "Sports", "Soccer", 10),
                newProduct("B", "1001", "Baseball glove", "Sports", "Baseball", 20),
                newProduct("B", "1002", "Football", "Sports", "Football", 50),
                newProduct("B", "1112", "Boston Red Sox Cap", "Clothing", "Hats", 40),
                newProduct("B", "3000", "Men's Jeans", "Clothing", "Pants", 15),
                newProduct("B", "3001", "Women's Jeans", "Clothing", "Pants", 10)
        ));

        Location charlotte = newLocation("C", "Charlotte", "E", newArrayList(
                newProduct("C", "1000", "Soccer ball", "Sports", "Soccer", 15),
                newProduct("C", "1002", "Football", "Sports", "Football", 60),
                newProduct("C", "3000", "Men's Jeans", "Clothing", "Pants", 20),
                newProduct("C", "3002", "Girls Shorts", "Clothing", "Shorts", 25)
        ));

        Location denver = newLocation("D", "Denver", "W", newArrayList(
                newProduct("D", "1000", "Soccer ball", "Sports", "Soccer", 5),
                newProduct("D", "1113", "Denver Broncos Hat", "Clothing", "Hats", 20),
                newProduct("D", "2001", "Denver Broncos Jersey", "Clothing", "Jerseys", 10),
                newProduct("D", "3000", "Men's Jeans", "Clothing", "Pants", 15),
                newProduct("D", "3001", "Women's Jeans", "Clothing", "Pants", 30),
                newProduct("D", "3003", "Women's T-shirts", "Clothing", "Shirts", 45),
                newProduct("D", "1003", "Ride Machete Snowboard", "Sports", "Snowboarding", 5)
        ));

        Location sanFran = newLocation("SF", "San Francisco", "W", newArrayList(
                newProduct("SF", "1000", "Soccer ball", "Sports", "Soccer", 0),
                newProduct("SF", "1113", "San Francisco 49ers Jersey", "Clothing", "Jerseys", 20),
                newProduct("SF", "3000", "Men's Jeans", "Clothing", "Pants", 10),
                newProduct("SF", "3001", "Women's Jeans", "Clothing", "Pants", 25),
                newProduct("SF", "3003", "Women's T-shirts", "Clothing", "Shirts", 45),
                newProduct("SF", "4000", "Surfboard", "Sports", "Surfing", 5)
        ));

        return newArrayList(atlanta, boston, charlotte, denver, sanFran);
    }

    static Location newLocation(String code, String name, String regionCode, List<Product> products) {
        return Location.builder()
                .code(code)
                .name(name)
                .regionCode(regionCode)
                .products(products)
                .build();
    }

    static Product newProduct(String locationCode,
                              String code,
                              String name,
                              String category,
                              String subCategory,
                              int quantity) {

        return Product.builder()
                .locationCode(locationCode)
                .code(code)
                .name(name)
                .category(category)
                .subCategory(subCategory)
                .quantity(quantity)
                .build();
    }
}
