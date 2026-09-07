package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
/**
    @Test
    void shouldCreateValidPassword() {
        Password pwd = new Password("My.Secret!Password#123");
    }

    @Test
    void shouldRejectNullPassword() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password(null)
        );
    }

    @Test
    void shouldRejectBlankPassword() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password(" ")
        );
    }

    @Test
    void shouldRejectPasswordWithLessThanEightCharacters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password("My.pwd1")
        );
    }

    @Test
    void shouldRejectPasswordWithoutUppercase() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password("my.secret!password#123")
        );
    }

    @Test
    void shouldRejectPasswordWithoutLowercase() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password("MY.SECRET!PASSWORD#123")
        );
    }

    @Test
    void shouldRejectPasswordWithoutNumber() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password("My.Secret!Password#")
        );
    }

    @Test
    void shouldRejectPasswordWithoutSymbol() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Password("MySecretPassword123")
        );
    }

    @Test
    void shouldReturnPasswordValue() {
        String value = "My.Secret!Password#123";

        Password pwd = new Password(value);

        assertEquals(value, pwd.getValue());
    }
*/

    @Test
    void shouldCreatePasswordFromHash() {
        String hash = "b47700cb17726a01eb211901f571d3dc";

        Password pwd = Password.fromHash(hash);

        assertEquals(hash, pwd.getValue());
    }

}
