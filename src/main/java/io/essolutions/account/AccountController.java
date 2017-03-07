package io.essolutions.account;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

public interface AccountController {

	public ResponseEntity<Account> me(Principal principal);
	
}
