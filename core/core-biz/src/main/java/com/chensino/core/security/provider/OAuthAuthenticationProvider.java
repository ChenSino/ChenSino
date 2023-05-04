package com.chensino.core.security.provider;

import com.chensino.core.api.entity.CustomSecurityUser;
import com.chensino.core.security.token.OAuthAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OAuthAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    @Bean
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("enter into custom OAuthAuthenticationProvider");
        CustomSecurityUser customSecurityUser = (CustomSecurityUser) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        OAuthAuthenticationToken oAuthAuthenticationToken = new OAuthAuthenticationToken(customSecurityUser, null);
        return oAuthAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuthAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
