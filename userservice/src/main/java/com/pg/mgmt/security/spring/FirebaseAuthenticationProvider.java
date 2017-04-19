package com.pg.mgmt.security.spring;

import com.google.appengine.api.users.UserServiceFactory;
import com.pg.mgmt.security.users.AppUser;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * A simple authentication provider which interacts with {@code AppUser} returned by the GAE
 * {@code UserService}, and also the local persistent {@code UserRegistry} to build an
 * application user principal.
 * <p>
 * If the user has been authenticated through google accounts, it will check if they are
 * already registered and either load the existing user information or assign them a
 * temporary identity with limited access until they have registered.
 * <p>
 * If the account has been disabled, a {@code DisabledException} will be raised.
 *
 * Created by Siva on 4/9/2017.
 */
public class FirebaseAuthenticationProvider implements AuthenticationProvider,
        MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

//    private UserRegistry userRegistry;

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        AppUser firebaseUser = (AppUser) authentication.getPrincipal();

//        AppUser appUser = userRegistry.findUser(googleUser.getUserId());
//        AppUser appUser = null;
//
//        if (appUser == null) {
//            // AppUser not in registry. Needs to register
//            appUser = new AppUser(googleUser.getUserId(), googleUser.getNickname(),
//                    googleUser.getEmail());
//            if(UserServiceFactory.getUserService().isUserLoggedIn() &&
//                    UserServiceFactory.getUserService().isUserAdmin()) {
//                appUser.getAuthorities().add(AppRole.ADMIN);
//            }
//        }
        //TODO

        if (!firebaseUser.isEnabled()) {
            throw new DisabledException("Account is disabled");
        }

        return new AppUserAuthentication(firebaseUser, authentication.getDetails());
    }

    /**
     * Indicate that this provider only supports PreAuthenticatedAuthenticationToken
     * (sub)classes.
     */
    public final boolean supports(Class<?> authentication) {
        return FirebasePreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

//    public void setUserRegistry(UserRegistry userRegistry) {
//        this.userRegistry = userRegistry;
//    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
