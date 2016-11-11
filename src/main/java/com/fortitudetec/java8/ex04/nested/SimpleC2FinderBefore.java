package com.fortitudetec.java8.ex04.nested;

/**
 * Pre-Java 8 implementation to find the first instance of C2 within the A data structure.
 * This implementation assumes that the argument and nested collections are not null.
 */
public class SimpleC2FinderBefore {

    public C2 findFirstC2(A a) {
        for (B b : a.getBs()) {
            for (C c : b.getCs()) {
                if (c instanceof C2) {
                    return (C2) c;
                }
            }
        }
        return null;
    }

}
