package io.essolutions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
        	
	private static final String RESOURCE_ID = "sampleResource";
  	
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
                    
    @Autowired
    private TokenStore tokenStore;
    
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
            .inMemory()
                .withClient("client")
                    .secret("secret")
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("authorization_code", "implicit", "password")
                    .authorities("ROLE_CLIENT")
                    .scopes("read", "write");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        configurer
            .tokenStore(tokenStore)
            .accessTokenConverter(accessTokenConverter)
            .authenticationManager(authenticationManager)
            .userApprovalHandler(new DefaultUserApprovalHandler());
    }
           
}
