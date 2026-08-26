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
}
