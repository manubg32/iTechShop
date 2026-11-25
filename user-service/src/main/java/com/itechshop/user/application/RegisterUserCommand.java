package com.itechshop.user.application;

public record RegisterUserCommand(String username, String email, String plainPassword) {

    // To avoid exposing the plain password in the toString() method when logging or debugging, we only include the username and email.
    @Override
    public String toString() {
        return "RegisterUserCommand[" + "username=" + username +", email=" + email + "]";
    }

}
