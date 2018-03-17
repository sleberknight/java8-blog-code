package com.fortitudetec.java8.ex05.flatmap;

import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class Product {

    private String code;
    private String name;
    private String category;
    private String subCategory;
    private int quantity;
    private String locationCode;

    public boolean hasName(String name) {
        return requireNonNull(name).equals(this.name);
    }

    public String getUniqueId() {
        return locationCode + code;
    }

}
