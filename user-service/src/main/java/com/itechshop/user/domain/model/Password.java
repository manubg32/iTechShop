package com.itechshop.user.domain.model;

public class Password {

    private final String value;

    private Password (String hash) {
        this.value = hash;
    }

    public static Password fromHash(String hash) {

        if (hash == null) {
            throw new IllegalArgumentException("Password hash must not be null");
        }
        if (hash.isBlank()) {
            throw new IllegalArgumentException("Password hash must not be blank");
        }

        return new Password(hash);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Password other)) {
            return false;
        }

        return this.value.equals(other.value);
    }
}
