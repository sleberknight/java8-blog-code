package com.fortitudetec.java8.ex04a.nested;

import java.util.List;

/**
 * Pre-Java 8 implementation to find the first instance of {@link GMailEmailAddress} in a {@link ContactRepository}.
 * <p>
 * Accompanies blog 'Towards More Functional Java - Digging into Nested Data Structures'. URLs:
 * <p>
 * http://www.fortitudetec.com/blogs/2016/11/11/towards-more-functional-java-dig-data-structures
 * <p>
 * http://www.sleberknight.com/blog/sleberkn/entry/towards_more_functional_java_digging
 */
public class GMailFinderBefore {

    /**
     * This implementation assumes the returned collections could be null.
     */
    public GMailEmailAddress findFirstGMailAddress(ContactRepository contactRepository) {
        if (contactRepository == null || contactRepository.getContacts() == null) {
            return null;
        }

        for (Contact contact : contactRepository.getContacts()) {
            List<EmailAddress> emailAddresses = contact.getEmailAddresses();
            if (emailAddresses == null) {
                continue;
            }

            for (EmailAddress address : emailAddresses) {
                if (address instanceof GMailEmailAddress) {
                    return (GMailEmailAddress) address;
                }
            }
        }
        return null;
    }

}
