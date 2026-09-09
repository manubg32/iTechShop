package com.itechshop.user.infrastructure.security;

import com.itechshop.user.domain.model.Password;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BCryptPasswordHasherTest {

    @Test
    void shouldHashPassword() {
        String plainPassword = "My.Secret!Password#123";

        BCryptPasswordHasher hasher = new BCryptPasswordHasher();

        Password password = hasher.hash(plainPassword);

        assertNotEquals(plainPassword, password.getValue());
    }

    @Test
    void shouldMatchSamePasswords() {
        String plainPassword = "My.Secret!Password#123";

        BCryptPasswordHasher hasher = new BCryptPasswordHasher();

        Password password = hasher.hash(plainPassword);

        assertTrue(hasher.matches(plainPassword, password));
    }

    @Test
    void shouldNotMatchDifferentPasswords () {
        String plainPassword = "My.Secret!Password#123";
        String differentPlainPassword = "Not.2Secret!Password#123";

        BCryptPasswordHasher hasher = new BCryptPasswordHasher();

        Password password = hasher.hash(plainPassword);

        assertFalse(hasher.matches(differentPlainPassword, password));
    }

}
