package com.fortitudetec.java8.ex04.nested;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder
public class B {

    @Singular
    private List<C> cs;

}
