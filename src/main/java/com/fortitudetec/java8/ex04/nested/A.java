package com.fortitudetec.java8.ex04.nested;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * Intended to represent a nested data structure, e.g. one that comes from some external XML file and which
 * you have no control over. This example represent the following XML:
 * <p>
 * <pre><code>
 * &lt;a>
 *   &lt;b>
 *     &lt;c type="C1" /&gt;
 *     &lt;c type="C2" /&gt;
 *     &lt;c type="C3" /&gt;
 *   &lt;/b>
 *   &lt;b>
 *     &lt;c type="C1" /&gt;
 *     &lt;c type="C2" /&gt;
 *     &lt;c type="C3" /&gt;
 *   &lt;/b>
 * &lt;/a>
 * </code></pre>
 */
@Getter
@Setter
@Builder
public class A {

    @Singular
    private List<B> bs;

}
