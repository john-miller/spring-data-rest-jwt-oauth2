package io.essolutions.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2ClientSecurityContextFactory.class)
public @interface WithMockOAuth2User {

	String value() default "user";

	String username() default "";

	String[] roles() default { "USER" };

	String[] authorities() default {};

	String password() default "password";
	
    String[] scopes() default {};
    
}