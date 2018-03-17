package com.fortitudetec.java8.ex05.flatmap;

import lombok.Builder;
import lombok.Value;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class Location {

    private String code;
    private String name;
    private String regionCode;
    private List<Product> products;

    public boolean isInRegion(String regionCode) {
        return requireNonNull(regionCode).equals(this.regionCode);
    }

}
