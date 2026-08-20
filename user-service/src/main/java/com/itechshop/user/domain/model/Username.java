package com.itechshop.user.domain.model;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Username {

    private final String value;

    public Username(String value) {
        if (value != null){
            value = value.toLowerCase();
            if (value.length() >= 3 && value.length() <= 20) {
                Pattern pattern = Pattern.compile("^[a-z0-9]+$");
                Matcher matcher = pattern.matcher(value);
                if (matcher.matches()){
                    this.value = value;
                } else {
                    throw new IllegalArgumentException("Username must contain only alphanumerical characters");
                }
            } else {
                throw new IllegalArgumentException("Username must contain between 3 and 20 characters");
            }
        } else {
            throw new IllegalArgumentException("Username mustn't be empty");
        }
    }

    public String getValue(){
        return value;
    }

}
