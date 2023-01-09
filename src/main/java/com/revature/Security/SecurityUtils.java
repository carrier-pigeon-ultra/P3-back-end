package com.revature.Security;

import com.revature.models.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
public class SecurityUtils {

    public static  String currentLogin() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
       final Authentication authentication = securityContext.getAuthentication();
        String login = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                final UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                login = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                login = (String) authentication.getPrincipal();
            }
        }
        return login;
    }

    public static User currentProfile() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        User profile = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                profile = (User) authentication.getPrincipal();
            }
        }
        return profile;
    }
}
