package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserIdTest {
    @Test
    void shouldCreateValidUserId() {
        UUID id = UUID.randomUUID();
        UserId userId = new UserId(id);

        assertEquals(id, userId.getValue());
    }

    @Test
    void shouldRejectNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> new UserId(null));
    }

    @Test
    void shouldConsiderSameUuidValueAsEqual() {
        UUID id = UUID.randomUUID();

        UserId id1 = new UserId(id);
        UserId id2 = new UserId(id);

        assertEquals(id1, id2);
    }

    @Test
    void shouldHaveSameHashCodeEqualUserId() {
        UUID id = UUID.randomUUID();

        UserId id1 = new UserId(id);
        UserId id2 = new UserId(id);

        assertEquals(id1.hashCode(), id2.hashCode());
    }


}
