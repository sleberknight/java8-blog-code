package com.fortitudetec.java8.ex04a.nested;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 8 implementation to find {@link EmailAddress}es in a {@link ContactRepository}.
 * <p>
 * Accompanies blog 'Towards More Functional Java - Digging into Nested Data Structures'. URLs:
 * <p>
 * http://www.fortitudetec.com/blogs/2016/11/11/towards-more-functional-java-dig-data-structures
 * <p>
 * http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_digging
 */
public class EmailFinderAfter {

    public <T extends EmailAddress> Optional<T> findFirst(ContactRepository contacts, Class<T> clazz) {
        return toStreamOfEmail(contacts, clazz).findFirst();
    }

    public <T extends EmailAddress> Optional<T> findAny(ContactRepository contacts, Class<T> clazz) {
        return toStreamOfEmail(contacts, clazz).findAny();
    }

    public <T extends EmailAddress> List<T> findAll(ContactRepository contacts, Class<T> clazz) {
        return toStreamOfEmail(contacts, clazz).collect(Collectors.toList());
    }

    /**
     * This implementation assumes that the collections could be null and defensively checks these cases.
     */
    private <T extends EmailAddress> Stream<T> toStreamOfEmail(ContactRepository contacts, Class<T> clazz) {
        return Optional.ofNullable(contacts)
                .map(ContactRepository::getContacts)
                .orElseGet(Lists::newArrayList)
                .stream()
                .flatMap(this::toStreamOfEmail)
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    private Stream<? extends EmailAddress> toStreamOfEmail(Contact contact) {
        return Optional.ofNullable(contact.getEmailAddresses())
                .orElseGet(Lists::newArrayList)
                .stream();
    }

}
