package com.fortitudetec.java8.ex05.flatmap;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Location {

    private String code;
    private String name;
    private List<Product> products;

}
