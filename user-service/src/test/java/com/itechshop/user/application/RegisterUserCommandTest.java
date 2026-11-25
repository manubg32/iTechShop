package com.itechshop.user.application;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class RegisterUserCommandTest {

    @Test
    public void shouldNotExposePlainPasswordInToString() {
        String plainPassword = "My.secret!Password#123";
        RegisterUserCommand userCommand = new RegisterUserCommand("user", "mail@example.com", plainPassword);
        assertFalse(userCommand.toString().contains(plainPassword));
    }

}
