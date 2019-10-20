package com.griffoul.mathieu.bbfback.authentication.jwt.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private Map<String, String> authorizedCredentials = getCredentials();

    @Bean
    public PasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (authorizedCredentials.containsKey(username)) {
            return new User(username, passwordEncoder2().encode(authorizedCredentials.get(username)),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Map<String, String> getCredentials() {
        Map<String, String> map = new HashMap<>();
        map.put("javainuse", "password");
        map.put("toto", "tata");
        map.put("griff", "1234");
        return map;
    }

}
