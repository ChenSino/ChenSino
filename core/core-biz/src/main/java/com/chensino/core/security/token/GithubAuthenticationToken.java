package com.chensino.core.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

public class GithubAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private Object credentials;

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     *
     */
    public GithubAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     * @param principal
     * @param credentials
     * @param authorities
     */
    public GithubAuthenticationToken(Object principal, Object credentials,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    /**
     * This factory method can be safely used by any code that wishes to create a
     * unauthenticated <code>UsernamePasswordAuthenticationToken</code>.
     * @param principal
     * @param credentials
     * @return UsernamePasswordAuthenticationToken with false isAuthenticated() result
     *
     * @since 5.7
     */
    public static GithubAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new GithubAuthenticationToken(principal, credentials);
    }

    /**
     * This factory method can be safely used by any code that wishes to create a
     * authenticated <code>UsernamePasswordAuthenticationToken</code>.
     * @param principal
     * @param credentials
     * @return UsernamePasswordAuthenticationToken with true isAuthenticated() result
     *
     * @since 5.7
     */
    public static GithubAuthenticationToken authenticated(Object principal, Object credentials,
                                                          Collection<? extends GrantedAuthority> authorities) {
        return new GithubAuthenticationToken(principal, credentials, authorities);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
