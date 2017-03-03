package io.essolutions.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Logger logger = Logger.getLogger(WebSecurity.class);
	
	public WebSecurity() {
		logger.info(this.getClass() + " has been created.");
	}
		
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	} 	
		
	@Autowired
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {		
		auth.inMemoryAuthentication().withUser("test").password("test").roles("USER","ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
		.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**")
			.permitAll()
			.and()
			.addFilterBefore(new ClientCredentialsTokenEndpointFilter(), BasicAuthenticationFilter.class);

	}

}