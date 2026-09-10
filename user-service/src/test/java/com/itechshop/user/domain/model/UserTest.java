package com.itechshop.user.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldCreateValidUser() {
        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Email email = new Email("Example.user@contoso.com");
        Password password =  Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        assertDoesNotThrow(
                () -> new User(userId, username, email, password, role)
        );
    }

    @Test
    void shouldReturnUserAttributes () {

        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Email email = new Email("Example.user@contoso.com");
        Password password =  Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        User user = new User(userId, username, email, password, role);

        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void shouldChangeUsername() {

        UserId userId = new UserId(UUID.randomUUID());
        Username oldUsername = new Username("exampleUser");
        Username newUsername = new Username("newUsername");
        Email email = new Email("Example.user@contoso.com");
        Password password = Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        User user = new User(userId, oldUsername, email, password, role);

        user.changeUsername(newUsername);

        assertEquals(newUsername, user.getUsername());
    }

    @Test
    void shouldChangeEmail () {
        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Email oldEmail = new Email("Example.user@contoso.com");
        Email newEmail = new Email("New.user@contoso.com");
        Password password = Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        User user = new User(userId, username, oldEmail, password, role);

        user.changeEmail(newEmail);

        assertEquals(newEmail, user.getEmail());
    }

    @Test
    void shouldChangePassword () {
        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Email email = new Email("Example.user@contoso.com");
        Password oldPassword = Password.fromHash("some-hash");
        Password newPassword = Password.fromHash("new-hash");
        Role role = Role.CUSTOMER;

        User user = new User(userId, username, email, oldPassword, role);

        user.changePassword(newPassword);

        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void shouldNotCreateUserWithNullUserId() {
        Username username = new Username("exampleUser");
        Email email = new Email("Example.user@contoso.com");
        Password password = Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        assertThrows(
                IllegalArgumentException.class,
                () -> new User(null, username, email, password, role)
        );
    }

   @Test
   void shouldNotCreateUserWithNullUsername() {

        UserId userId = new UserId(UUID.randomUUID());
        Email email = new Email("Example.user@contoso.com");
        Password password = Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        assertThrows(
                IllegalArgumentException.class,
                () -> new User(userId, null, email, password, role)
        );   
   }

   @Test
   void shouldNotCreateUserWithNullEmail() {

        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Password password = Password.fromHash("some-hash");
        Role role = Role.CUSTOMER;

        assertThrows(
                IllegalArgumentException.class,
                () -> new User(userId, username, null, password, role)
        );   
   }

   @Test
   void shouldNotCreateUserWithNullPassword() {

        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Email email = new Email("Example.user@contoso.com");
        Role role = Role.CUSTOMER;

        assertThrows(
                IllegalArgumentException.class,
                () -> new User(userId, username, email, null, role)
        );   
   }

   @Test 
   void shouldNotCreateUserWithNullRole() {
        UserId userId = new UserId(UUID.randomUUID());
        Username username = new Username("exampleUser");
        Email email = new Email("Example.user@contoso.com");
        Password password = Password.fromHash("some-hash");
        Role role = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> new User(userId, username, email, password, role)
        );
   }

}
