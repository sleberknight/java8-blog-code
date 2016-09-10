package com.fortitudetec.java8.ex04a.nested;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailFinderAfterTest {

    private EmailFinderAfter emailFinder;

    @Before
    public void setUp() {
        emailFinder = new EmailFinderAfter();
    }

    @Test
    public void testFindFirstGMail_WhenNullContactRepository() {
        assertThat(
                emailFinder.findFirst(null, GMailEmailAddress.class).orElse(null))
                .isNull();
    }

    @Test
    public void testFindFirstGMail_WhenEmptyContacts() {
        ContactRepository contacts = ContactRepository.builder()
                .contacts(Lists.newArrayList())
                .build();
        assertThat(contacts.getContacts()).isEmpty();
        assertThat(
                emailFinder.findFirst(contacts, GMailEmailAddress.class).orElse(null))
                .isNull();
    }

    @Test
    public void testFindFirstGMail_WhenNullContacts() {
        ContactRepository contacts = ContactRepository.builder().build();
        contacts.setContacts(null);
        assertThat(
                emailFinder.findFirst(contacts, GMailEmailAddress.class).orElse(null))
                .isNull();
    }

    @Test
    public void testFindFirstGMail_WhenNoGMails() {
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("alice@yahoo.com"))
                        .emailAddress(new YahooEmailAddress("alice_2@yahoo.com"))
                        .emailAddress(new ExhangeEmailAddress("alice@exchange.com"))
                        .build())
                .build();
        assertThat(
                emailFinder.findFirst(contacts, GMailEmailAddress.class).orElse(null))
                .isNull();
    }

    @Test
    public void testFindFirstGMail_WhenEmailAddressesAreNull() {
        Contact contact = Contact.builder().build();
        contact.setEmailAddresses(null);
        ContactRepository contacts = ContactRepository.builder()
                .contact(contact)
                .build();
        assertThat(
                emailFinder.findFirst(contacts, GMailEmailAddress.class).orElse(null))
                .isNull();
    }

    @Test
    public void testFindFirstGMail_WhenOneGMail() {
        GMailEmailAddress gmail = new GMailEmailAddress("bob@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("alice@yahoo.com"))
                        .emailAddress(new ExhangeEmailAddress("alice@exchange"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(gmail)
                        .emailAddress(new ExhangeEmailAddress("bob@exchange.com"))
                        .build())
                .build();
        assertThat(
                emailFinder.findFirst(contacts, GMailEmailAddress.class).orElse(null))
                .isNotNull().isSameAs(gmail);
    }

    @Test
    public void testFindFirstGMail_WhenMultipleGMails() {
        GMailEmailAddress gmail = new GMailEmailAddress("bob@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("alice@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(gmail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new ExhangeEmailAddress("diane@exchange.com"))
                        .build())
                .build();
        assertThat(
                emailFinder.findFirst(contacts, GMailEmailAddress.class).orElse(null))
                .isNotNull().isSameAs(gmail);
    }

    @Test
    public void testFindFirstYahoo() {
        YahooEmailAddress yahoo = new YahooEmailAddress("alice@yahoo.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(yahoo)
                        .emailAddress(new YahooEmailAddress("alice_2@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();
        assertThat(
                emailFinder.findFirst(contacts, YahooEmailAddress.class).orElse(null))
                .isSameAs(yahoo);
    }

    @Test
    public void testFindFirstEmailAddressOfAnyType() {
        YahooEmailAddress firstEmail = new YahooEmailAddress("alice@yahoo.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(firstEmail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(new GMailEmailAddress("bob@gmail.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new ExhangeEmailAddress("diane@exchange.com"))
                        .build())
                .build();
        assertThat(
                emailFinder.findFirst(contacts, EmailAddress.class).orElse(null))
                .isSameAs(firstEmail);
    }

    @Test
    public void testFindAll_GMail() {
        GMailEmailAddress firstGMail = new GMailEmailAddress("bob@gmail.com");
        GMailEmailAddress secondGMail = new GMailEmailAddress("diane@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("alice@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(firstGMail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new ExhangeEmailAddress("diane@exchange.com"))
                        .emailAddress(secondGMail)
                        .build())
                .build();
        assertThat(
                emailFinder.findAll(contacts, GMailEmailAddress.class))
                .containsExactly(firstGMail, secondGMail);
    }

    @Test
    public void testFindAll_EmailAddresses() {
        EmailAddress firstEmail = new YahooEmailAddress("alice@yahoo.com");
        EmailAddress secondEmail = new YahooEmailAddress("bob@yahoo.com");
        EmailAddress thirdEmail = new GMailEmailAddress("bob@gmail.com");
        EmailAddress fourthEmail = new ExhangeEmailAddress("diane@exchange.com");
        EmailAddress fifthEmail = new GMailEmailAddress("diane@gmail.com");

        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(firstEmail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(secondEmail)
                        .emailAddress(thirdEmail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(fourthEmail)
                        .emailAddress(fifthEmail)
                        .build())
                .build();
        assertThat(emailFinder.findAll(contacts, EmailAddress.class))
                .containsExactly(firstEmail, secondEmail, thirdEmail, fourthEmail, fifthEmail);
    }

    @Test
    public void testFindAnyGMail_WhenOnlyOneGMail() {
        GMailEmailAddress onlyGMail = new GMailEmailAddress("bob@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("alice@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(onlyGMail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new ExhangeEmailAddress("diane@exchange.com"))
                        .build())
                .build();
        assertThat(
                emailFinder.findAny(contacts, GMailEmailAddress.class).orElse(null))
                .isSameAs(onlyGMail);
    }

    @Test
    public void testFindAnyGMail_WhenMoreThanOneOneGMail() {
        GMailEmailAddress firstGMail = new GMailEmailAddress("bob@gmail.com");
        GMailEmailAddress secondGMail = new GMailEmailAddress("diane@gmail.com");
        ContactRepository contacts = ContactRepository.builder()
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("alice@yahoo.com"))
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new YahooEmailAddress("bob@yahoo.com"))
                        .emailAddress(firstGMail)
                        .build())
                .contact(Contact.builder()
                        .emailAddress(new ExhangeEmailAddress("diane@exchange.com"))
                        .emailAddress(secondGMail)
                        .build())
                .build();
        assertThat(
                emailFinder.findAny(contacts, GMailEmailAddress.class).orElse(null))
                .isIn(firstGMail, secondGMail);
    }

}