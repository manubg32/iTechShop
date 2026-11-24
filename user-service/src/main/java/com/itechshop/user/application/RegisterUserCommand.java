package com.itechshop.user.application;

public record RegisterUserCommand(String username, String email, String plainPassword) {

}
