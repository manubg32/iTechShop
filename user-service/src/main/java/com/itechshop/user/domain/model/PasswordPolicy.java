package com.itechshop.user.domain.model;

import java.util.regex.Pattern;

public class PasswordPolicy {

    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile(".*[!@#$%^&*()\\-_+=.,?:;'\"<>/\\\\|\\[\\]{}~`].*");

    public static void validate(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Password must not be null");
        }

        if (value.isBlank()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        if (value.length() < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters");
        }

        if (!UPPER_CASE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one upper-case character");
        }

        if (!LOWER_CASE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one lower-case character");
        }

        if (!NUMBER_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }

        if (!SYMBOL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Password must contain at least one symbol");
        }
    }
}
