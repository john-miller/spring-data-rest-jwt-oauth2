package io.essolutions.account;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountControllerImpl implements AccountController {

	@Autowired
	private AccountRepository accountRepository;
	
	@PreAuthorize("#oauth2.hasScope('read') AND hasAnyRole('USER', 'ADMIN')")
	@RequestMapping("/me")
	@Override
	public ResponseEntity<Account> me(Principal principal) {
		Account account = accountRepository.findOneByUsername(principal.getName());
		if(account != null)
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
	}

	
	
}
