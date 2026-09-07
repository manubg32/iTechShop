package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordPolicyTest {

    @Test
    void shouldAcceptValidPassword() {
        assertDoesNotThrow(
                () -> PasswordPolicy.validate("My.Secret!Password#123")
        );
    }

    @Test
    void shouldRejectNullPassword() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate(null)
        );
    }

    @Test
    void shouldRejectBlankPassword() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate(" ")
        );
    }

    @Test
    void shouldRejectPasswordWithLessThanEightCharacters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate("My.pwd1")
        );
    }

    @Test
    void shouldRejectPasswordWithoutUppercase() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate("my.secret!password#123")
        );
    }

    @Test
    void shouldRejectPasswordWithoutLowercase() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate("MY.SECRET!PASSWORD#123")
        );
    }

    @Test
    void shouldRejectPasswordWithoutNumber() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate("My.Secret!Password#")
        );
    }

    @Test
    void shouldRejectPasswordWithoutSymbol() {
        assertThrows(
                IllegalArgumentException.class,
                () -> PasswordPolicy.validate("MySecretPassword123")
        );
    }

}
