package com.fortitudetec.java8.ex04.nested;

import java.util.List;

/**
 * Pre-Java 8 implementation to find the first instance of C2 within the A data structure.
 * <p>
 * Accompanies blog: TODO write the blog
 */
public class C2FinderBefore {

    public C2 findFirstC2(A a) {
        if (a == null || a.getBs() == null) {
            return null;
        }

        for (B b : a.getBs()) {
            List<C> cs = b.getCs();
            if (cs == null) {
                continue;
            }

            for (C c : cs) {
                if (c instanceof C2) {
                    return (C2) c;
                }
            }
        }
        return null;
    }

}
