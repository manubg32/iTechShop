package com.itechshop.user.domain.port;

import com.itechshop.user.domain.model.Password;

public interface PasswordHasher {

    Password hash(String plainPassword);
    boolean matches (String plainPassword, Password password);

}
