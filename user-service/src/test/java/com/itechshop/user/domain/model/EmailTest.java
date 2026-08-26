package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = new Email("John@Doe.Com");
        assertEquals("john@doe.com", email.getValue());
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email(null)
        );
    }

    @Test
    void shouldRejectEmptyEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("")
        );
    }

    @Test
    void shouldRejectEmailWithoutAtSign() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("johndoe.com")
        );
    }

    @Test
    void shouldRejectEmailWithoutUser() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("@doe.com")
        );
    }

    @Test
    void shouldRejectEmailWithoutDomain() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("john@")
        );
    }

    @Test
    void shouldRejectEmailWithoutTld() {
        assertThrows(
                IllegalArgumentException.class,
                () ->  new Email ("john@doe")
        );
    }

    @Test
    void shouldRejectEmailWithMoreThanOneAtSign() {
        assertThrows(
                IllegalArgumentException.class,
                () ->  new Email ("john@@doe.com")
        );
    }

    @Test
    void shouldRejectEmailWithInvalidDomain() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("john@doe..com")
        );
    }

    @Test
    void shouldRejectEmailWithSpaces() {
        assertThrows(
                IllegalArgumentException.class,
                () ->  new Email ("john @doe.com")
        );
    }

    @Test
    void shouldConsiderSameEmailValueAsEqual() {
        Email firstEmail = new Email("john.doe@example.com");
        Email secondEmail = new Email("John.Doe@Example.Com");
        assertEquals(firstEmail, secondEmail);
    }

    @Test
    void shouldNotConsiderDifferentEmailsAsEqual() {
        Email firstEmail = new Email("John@doe.com");
        Email secondEmail = new Email("John.doe@contoso.com");
        assertNotEquals(firstEmail, secondEmail);
    }

    @Test
    void shouldEqualEmailsHaveSameHashCode() {
        Email firstEmail = new Email("john.doe@contoso.com");
        Email secondEmail = new Email("John.doe@Contoso.com");
        assertEquals(firstEmail.hashCode(), secondEmail.hashCode());
    }

    @Test
    void shouldReturnNormalizedEmailAsString() {
        Email badFormattedEmail = new Email("John.Doe@Contoso.Com");
        assertEquals("john.doe@contoso.com", badFormattedEmail.toString());
    }

    @Test
    void shouldNotEqualNull() {
        Email email = new Email("john@doe.com");

        assertNotEquals(null, email);
    }

}
