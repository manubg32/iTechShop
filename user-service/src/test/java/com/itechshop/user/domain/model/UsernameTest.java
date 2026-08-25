package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsernameTest {

    @Test
    void shouldCreateValidUsername() {
        Username username = new Username("User123");
        assertEquals("user123", username.getValue());
    }

    @Test
    void shouldRejectNullUsername() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Username(null)
        );
    }

    @Test
    void shouldRejectUsernameWithInvalidLength() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("ab")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("ThisUserHasMoreThanTwentyCharacters")
        );
    }

    @Test
    void shouldAcceptUsernameWithMinimumLength() {
        Username threeChars = new Username("ab3");
        assertEquals("ab3", threeChars.getValue());
    }

    @Test
    void shouldAcceptUsernameWithMaximumLength() {
        Username twentyChars = new Username("theUserHasTwentyChrs");
        assertEquals("theuserhastwentychrs", twentyChars.getValue());
        /**
         * assertDoesNotThrow(
         *       () -> new Username("theUserHasTwentyChrs")
         * );
         */
    }

    @Test
    void shouldRejectUsernameWithInvalidCharacters() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("user-123")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("user_123")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Username("user 123")
        );
    }

    @Test
    void shouldConsiderSameUsernameValueAsEqual() {
        Username firstUser = new Username("User123");
        Username secondUser = new Username("user123");
        assertEquals(firstUser, secondUser);
        // assert(firstUser.equals(secondUser));
    }

    @Test
    void shouldNotConsiderDifferentUsernamesAsEqual() {
        Username firstUser = new Username("User123");
        Username secondUser = new Username("other123");
        assertNotEquals(firstUser, secondUser);
    }

    @Test
    void shouldEqualUsernamesHaveSameHashCode() {
        Username firstUser = new Username("User123");
        Username secondUser = new Username("user123");
        assertEquals(firstUser.hashCode(), secondUser.hashCode());
    }

}
