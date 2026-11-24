package com.itechshop.user.domain.model;

public class User {

    private final UserId userId;
    private Username username;
    private Email email;
    private Password password;
    private final Role role;

    public User(UserId userId, Username username, Email email, Password password, Role role) {
        this.userId = requireNotNull(userId, "UserId");
        this.username = requireNotNull(username, "Username");
        this.email = requireNotNull(email, "Email");
        this.password = requireNotNull(password, "Password");
        this.role = requireNotNull(role, "Role");
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
        this.username = requireNotNull(newUsername, "Username");
    }

    public void changeEmail(Email newEmail) {
        this.email = requireNotNull(newEmail, "Email");
    }

    public void changePassword(Password newPassword) {
        this.password = requireNotNull(newPassword, "Password");
    }

    private static <T> T requireNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }

    @Override
    public boolean equals(Object otherObject) {

        if (this == otherObject) return true;

        if (otherObject == null || getClass() != otherObject.getClass()) return false;
        User user = (User) otherObject;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}