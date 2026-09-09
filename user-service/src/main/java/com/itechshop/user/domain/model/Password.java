package com.itechshop.user.domain.model;

public class Password {

    private final String value;
/**
    public Password(String value) {

        PasswordPolicy.validate(value);

        this.value = value;
    }
*/
    private Password (String hash) {
        this.value = hash;
    }

    public static Password fromHash(String hash) {
        return new Password(hash);
    }

    public String getValue() {
        return value;
    }
}
