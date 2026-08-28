package com.itechshop.user.domain.model;

import java.util.UUID;

public class UserId {

    private final UUID value;

    public UserId(UUID value) {

        if (value == null) {
            throw new IllegalArgumentException("The UserId must not be null");
        }

        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof UserId)) {
            return false;
        }

        UserId other = (UserId) obj;

        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
