package io.essolutions.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

/**
 * Combines OAuth2 client attributes with user attributes
 * 
 * @author Jonathan
 */
public class WithMockOAuth2ClientSecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2User mockOAuth2User) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Set<String> scopes = new HashSet<>();
        for(String scope : mockOAuth2User.scopes())
        	scopes.add(scope);

        OAuth2Request request = new OAuth2Request(null, null, null, true, scopes, null, null, null, null);

        String username = StringUtils.hasLength(mockOAuth2User.username()) ? mockOAuth2User.username() : mockOAuth2User.value();
		if (username == null) {
			throw new IllegalArgumentException(mockOAuth2User + " cannot have null username on both username and value properites");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (String authority : mockOAuth2User.authorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}

		if(grantedAuthorities.isEmpty()) {
			for (String role : mockOAuth2User.roles()) {
				if (role.startsWith("ROLE_")) {
					throw new IllegalArgumentException("roles cannot start with ROLE_ Got "
							+ role);
				}
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
		} else if(!(mockOAuth2User.roles().length == 1 && "USER".equals(mockOAuth2User.roles()[0]))) {
			throw new IllegalStateException("You cannot define roles attribute "+ Arrays.asList(mockOAuth2User.roles())+" with authorities attribute "+ Arrays.asList(mockOAuth2User.authorities()));
		}
		
		User principal = new User(username, mockOAuth2User.password(), true, true, true, true,	grantedAuthorities);	
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
		Authentication auth = new OAuth2Authentication(request, authentication);
        context.setAuthentication(auth);
       
        return context;
    }
}