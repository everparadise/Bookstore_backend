package com.example.main.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 对用户密码进行加密 并进行登录验证
@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    /* self-defined auth logic */
    @Override
    public Authentication authenticate(Authentication authenticationToken)
            throws AuthenticationException {
        String username = String.valueOf(authenticationToken.getPrincipal());
        String password = String.valueOf(authenticationToken.getCredentials());

        //passwordEncoder.encode(userDetails.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("AuthProvider: " + userDetails.getPassword());
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            /* password match */
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }

        /* password mismatch */
        throw new BadCredentialsException("Invalid password");
    }

    /* reflection: make sure to support */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }


}
