package com.fortitudetec.java8.ex04a.nested;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GMailFinderBeforeTest {

    private GMailFinderBefore gmailFinder;

    @Before
    public void setUp() {
        gmailFinder = new GMailFinderBefore();
    }

    @Test
    public void testFindFirstGMail_WhenNullContactRepository() {
        assertThat(gmailFinder.findFirstGMailAddress(null)).isNull();
    }

    @Test
    public void testFindFirstGMail_WhenEmptyContacts() {
        ContactRepository contacts = ContactRepository.builder()
                .contacts(Lists.newArrayList())
                .build();
        assertThat(contacts.getContacts()).isEmpty();
        assertThat(gmailFinder.findFirstGMailAddress(contacts)).isNull();
    }

    @Test
    public void testFindFirstGMail_WhenNoGMails() {
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new ExhangeEmailAddress("sally@exchange.com"))
                        .emailAddress(new YahooEmailAddress("sally@yahoo.com"))
                        .build())
                .build();
        assertThat(gmailFinder.findFirstGMailAddress(contacts)).isNull();
    }

    @Test
    public void testFindFirstGMail_WhenEmailsAreNull() {
        Contact contact = Contact.builder().build();
        contact.setEmailAddresses(null);
        assertThat(contact.getEmailAddresses()).isNull();
        ContactRepository contacts = ContactRepository.builder()
                .contact(contact)
                .build();
        assertThat(gmailFinder.findFirstGMailAddress(contacts)).isNull();
    }

    @Test
    public void testFindFirstGMail_WhenOneGMail() {
        GMailEmailAddress gmail = new GMailEmailAddress("bob@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("sam@yahoo.com"))
                        .emailAddress(new ExhangeEmailAddress("sam@exchange.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(gmail)
                        .emailAddress(new ExhangeEmailAddress("bob@exchange.com"))
                        .build())
                .build();
        assertThat(gmailFinder.findFirstGMailAddress(contacts)).isSameAs(gmail);
    }

    @Test
    public void testFindFirstGMail_WhenMultipleGMails() {
        GMailEmailAddress gmail = new GMailEmailAddress("bob@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("sam@yahoo.com"))
                        .emailAddress(new ExhangeEmailAddress("sam@exchange.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(gmail)
                        .emailAddress(new GMailEmailAddress("bob_2@gmail.com"))
                        .emailAddress(new ExhangeEmailAddress("bob@exchange.com"))
                        .build())
                .build();
        assertThat(gmailFinder.findFirstGMailAddress(contacts)).isSameAs(gmail);
    }

}