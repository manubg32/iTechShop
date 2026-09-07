package com.itechshop.user.domain.model;

import java.util.regex.Pattern;

public class Password {

    private final String value;

    public Password(String value) {

        PasswordPolicy.validate(value);

        //PasswordHasher.hash(value);

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
