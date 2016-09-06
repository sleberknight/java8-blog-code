package com.fortitudetec.java8.ex04.nested;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class C {

    private String data;

    protected C(String data) {
        this.data = data;
    }

}
