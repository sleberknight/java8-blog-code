package com.fortitudetec.java8.ex05.flatmap;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Product {

    private String code;
    private String name;
    private String category;
    private String subCategory;
    private int quantity;
    private String locationCode;

}
