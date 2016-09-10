package com.fortitudetec.java8.ex04a.nested;

import lombok.AccessLevel;
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
 * &lt;contacts>
 *   &lt;contact>
 *     &lt;name>Alice&lt;/name>
 *     &lt;email type="yahoo" address="alice@yahoo.com" /&gt;
 *     &lt;email type="gmail" address="alice@gmail.com" /&gt;
 *     &lt;email type="exchange" address="alice@exchange.com" /&gt;
 *   &lt;/contact>
 *   &lt;contact>
 *     &lt;name>Bob&lt;/name>
 *     &lt;email type="gmail" address="bob@gmail.com" /&gt;
 *     &lt;email type="yahoo" address=""bob@yahoo.com /&gt;
 *   &lt;/contact>
 * &lt;/contacts>
 * </code></pre>
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@Builder
public class ContactRepository {

    @Singular
    private List<Contact> contacts;

}
