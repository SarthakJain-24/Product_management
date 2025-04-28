package com.example.productmanagement.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}sarthak")
                    .roles("ADMIN")
                    .build();
        } else if ("user".equals(username)) {
            return User.withUsername("user")
                    .password("{noop}password")
                    .roles("USER")
                    .build();
        } else {
            throw new RuntimeException("User not found");
        }
    }
}