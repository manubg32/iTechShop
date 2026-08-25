package com.itechshop.user.domain.model;

import java.util.Locale;
import java.util.regex.Pattern;

public class Username {
    private static final Pattern PATTERN = Pattern.compile("^[a-z0-9]+$");

    private final String value;

    public Username(String value) {
        // Guard Clauses
        if (value == null) {
            throw new IllegalArgumentException("Username must not be null");
        }
        value = value.toLowerCase(Locale.ROOT); // Locale-independent normalization
        if (value.length() < 3 || value.length() > 20) {
            throw new IllegalArgumentException("Username must contain between 3 and 20 characters");
        }
        if (PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Username must contain only alphanumerical characters");
        }
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Username)) {
            return false;
        }

        Username other = (Username) obj;

        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
