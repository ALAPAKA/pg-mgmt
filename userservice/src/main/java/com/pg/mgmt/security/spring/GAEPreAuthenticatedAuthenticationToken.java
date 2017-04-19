package com.pg.mgmt.security.spring;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

/**
 * Created by Siva on 4/16/2017.
 */
public class GAEPreAuthenticatedAuthenticationToken extends PreAuthenticatedAuthenticationToken {
    public GAEPreAuthenticatedAuthenticationToken(Object aPrincipal, Object aCredentials) {
        super(aPrincipal, aCredentials);
    }

    public GAEPreAuthenticatedAuthenticationToken(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }
}
