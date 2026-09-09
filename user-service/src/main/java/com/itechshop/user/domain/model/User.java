package com.itechshop.user.domain.model;

public class User {

    private final UserId userId;
    private Username username;
    private Email email;
    private Password password;
    private final Role role;

    public User(UserId userId, Username username, Email email, Password password, Role role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserId getUserId() {
        return userId;
    }

    public Username getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void changeUsername(Username newUsername) {
        this.username = newUsername;
    }

    public void changeEmail(Email newEmail) {
        this.email = newEmail;
    }

    public void changePassword(Password newPassword) {
        this.password = newPassword;
    }
}
