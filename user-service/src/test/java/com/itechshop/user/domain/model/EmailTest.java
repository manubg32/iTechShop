package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
