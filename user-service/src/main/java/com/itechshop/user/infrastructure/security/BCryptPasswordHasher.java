package com.itechshop.user.infrastructure.security;

import com.itechshop.user.domain.model.Password;
import com.itechshop.user.domain.port.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Password hash(String plainPassword) {
        String encodedPassword = encoder.encode(plainPassword);

        return Password.fromHash(encodedPassword);
    }

    @Override
    public boolean matches(String plainPassword, Password password) {
        return encoder.matches(plainPassword, password.getValue());
    }
}
