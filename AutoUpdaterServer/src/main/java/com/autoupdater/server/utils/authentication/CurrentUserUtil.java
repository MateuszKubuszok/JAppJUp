package com.autoupdater.server.utils.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility helpful in dealing with current user's authentication.
 */
public class CurrentUserUtil {
    /**
     * Returns current user's name or null if none authenticated;
     * 
     * @return username if available, null otherwise
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? (String) authentication.getPrincipal() : null;
    }
}
