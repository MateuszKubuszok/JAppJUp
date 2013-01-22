package com.autoupdater.server.utils.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.autoupdater.server.models.User;
import com.autoupdater.server.services.UserService;

/**
 * Authentication manager using BCrypt to encrypt user's password.
 */
@SuppressWarnings("deprecation")
public class BCryptAuthenticationManager implements AuthenticationManager {
    /**
     * Manager's logger.
     */
    protected static Logger logger = Logger.getLogger("Authentication Manager");

    /**
     * UserService instance.
     */
    @Autowired
    private UserService userService;

    /**
     * Authenticate user.
     * 
     * @param auth
     *            authentication data passed by Spring Security
     * @return result of authentication
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        logger.debug("Performing authentication");

        User user = null;

        logger.debug("Searching user [" + auth.getName() + "] in DB");
        try {
            user = userService.findByUsername(auth.getName());
        } catch (Exception e) {
            logger.error("User [" + auth.getName() + "] does not exists (exception)!");
            throw new AuthenticationServiceException("Error while obtaining User data!");
        }
        if (user == null) {
            logger.error("User [" + auth.getName() + "] does not exists (null)!");
            throw new BadCredentialsException("User does not exists!");
        }

        if (!BCrypt.checkpw(auth.getCredentials().toString(), user.getHashedPassword())) {
            logger.error("Password doesn't match!");
            throw new BadCredentialsException("Password doesn't match!");
        }

        logger.debug("User details are good and ready to go");
        return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(),
                getAuthorities(user.isAdmin(), user.isPackageAdmin()));
    }

    /**
     * Creates collection of authorities basing on user data.
     * 
     * @param admin
     *            whether user is admin
     * @param packageAdmin
     *            whether user is package admin
     * @return collection of authorities
     */
    public Collection<GrantedAuthority> getAuthorities(boolean admin, boolean packageAdmin) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(3);

        logger.debug("Grant ROLE_USER to this user");
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));

        if (admin) {
            logger.debug("Grant ROLE_ADMIN to this user");
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }

        if (packageAdmin) {
            logger.debug("Grant ROLE_PACKAGE_ADMIN to this user");
            authList.add(new GrantedAuthorityImpl("ROLE_PACKAGE_ADMIN"));
        }

        return authList;
    }
}