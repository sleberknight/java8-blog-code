package com.fortitudetec.java8.ex04.nested;

import java.util.List;

/**
 * Pre-Java 8 implementation to find the first instance of C2 within the A data structure.
 * This implementation assumes that the collections could be null and defensively checks these cases.
 * <p>
 * Accompanies blog 'Towards More Functional Java - Digging into Nested Data Structures'. URLs:
 * <p>
 * http://www.fortitudetec.com/blogs/2016/11/11/towards-more-functional-java-dig-data-structures
 * <p>
 * http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_digging
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
