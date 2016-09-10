package com.fortitudetec.java8.ex04a.nested;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter(AccessLevel.PACKAGE)
@Builder
public class Contact {

    @Singular
    private List<EmailAddress> emailAddresses;
}
