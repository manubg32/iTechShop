package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    void shouldReturnPasswordValue() {
        String hash = "b47700cb17726a01eb211901f571d3dc";

        Password pwd = Password.fromHash(hash);

        Assertions.assertEquals(hash, pwd.getValue());
    }

    @Test
    void shouldConsiderSameHashAsEqual() {
        String hash = "some-hash";

        Password pwd1 = Password.fromHash(hash);
        Password pwd2 = Password.fromHash(hash);

        Assertions.assertEquals(pwd1, pwd2);
    }

    @Test
    void shouldHaveSameHashCodeForEqualPassword() {
        String hash = "some-hash";

        Password pwd1 = Password.fromHash(hash);
        Password pwd2 = Password.fromHash(hash);

        Assertions.assertEquals(pwd1.hashCode(), pwd2.hashCode());
    }

    @Test
    void shouldRejectNullHash() {
        String hash = null;

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Password.fromHash(hash)
        );
    }

    @Test
    void shouldRejectBlankHash() {
        String hash = "";

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Password.fromHash(hash)
        );
    }

}
