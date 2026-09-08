package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    void shouldReturnPasswordValue() {
        String hash = "b47700cb17726a01eb211901f571d3dc";

        Password pwd = Password.fromHash(hash);

        assertEquals(hash, pwd.getValue());
    }

}
