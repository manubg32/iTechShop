package com.itechshop.user.domain.model;

import java.util.Locale;

public class Email {
    private final String value;

    public Email(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        if (value.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        value = value.toLowerCase(Locale.ROOT);

        if (value.indexOf('@') == -1) {
            throw new IllegalArgumentException("Email must contain one '@'");
        }

        if (value.indexOf('@') != value.lastIndexOf('@')) {
            throw new IllegalArgumentException("Email must not contain more than one '@'");
        }

        if (value.indexOf('@') == 0) {
            throw new IllegalArgumentException("Email must contain a user");
        }

        if (value.indexOf('@') == value.length() - 1) {
            throw new IllegalArgumentException("Email must contain a domain");
        }

        if (value.lastIndexOf('.') < value.indexOf('@')) {
            throw new IllegalArgumentException("Email must contain a TLD");
        }

        if (value.contains(" ")) {
            throw new IllegalArgumentException("Email must not contain spaces");
        }

        if (value.indexOf('.') == -1) {
            throw new IllegalArgumentException("Email must contain a TLD");
        }

        if (value.substring(value.indexOf('@')).contains("..")) {
            throw new IllegalArgumentException("Email domain must not contain consecutive dots");
        }

        if (value.indexOf('.') == 0) {
            throw new IllegalArgumentException("Email must contain a user and a domain");
        }

        if (value.indexOf('.') == value.length() - 1) {
            throw new IllegalArgumentException("Email must contain a TLD");
        }

        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Email)) {
            return false;
        }
        Email other = (Email) obj;

        return this.value.equals(other.getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.value;
    }
}
