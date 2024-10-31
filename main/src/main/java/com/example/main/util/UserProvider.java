package com.example.main.util;

import com.example.main.Constants.Role;
import com.example.main.model.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserProvider {
    // private util method， pass request user info to controller
    public static Long getUserUid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((AuthUser)authentication.getPrincipal()).getUser().getUid();
    }

    public static Role getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((AuthUser)authentication.getPrincipal()).getUser().getRole();
    }
    public static String getToken(){
        getUserRole();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getCredentials();
    }

}
