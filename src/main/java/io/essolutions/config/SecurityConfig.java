package io.essolutions.config;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${username.query}")
	private String usernameQuery;
	
	@Value("${authorities.by.username}")
	private String authoritiesByUsername;
	
	@Autowired
	private DataSource dataSource;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http
          	.sessionManagement()
          	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
	         .requestMatcher(new BasicRequestMatcher())
	         .authorizeRequests()
	         .antMatchers(HttpMethod.POST, "/oauth/token")
	         .authenticated()
	     .and()
	         .httpBasic();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			.usersByUsernameQuery(usernameQuery)
			.authoritiesByUsernameQuery(authoritiesByUsername);
    }
    
    /**
     * This is important, it stops spring security from checking bearer authorization requests.
     * 
     * @author Jonathan
     *
     */
	private static class BasicRequestMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			return (auth != null && auth.startsWith("Basic"));
		}
	}

}
