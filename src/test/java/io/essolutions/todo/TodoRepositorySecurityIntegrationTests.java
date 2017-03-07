package io.essolutions.todo;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import io.essolutions.common.WithMockOAuth2User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoRepositorySecurityIntegrationTests {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Test(expected = AccessDeniedException.class)
	@WithAnonymousUser
	public void findAllWithAnonymousUser() {
		todoRepository.findAll(new PageRequest(0,1));
	}
	
	@Test(expected = AccessDeniedException.class)
	@WithMockUser(roles="USER")
	public void findAllWithRegularUser() {
		todoRepository.findAll();
	}
	
	@Test(expected = AccessDeniedException.class)
	@WithMockOAuth2User(roles="USER")
	public void findAllPagedWithRegularUser() {
		todoRepository.findAll(new PageRequest(0,1));
	}
	
	@Test
	@WithMockOAuth2User(roles="ADMIN")
	public void findAllWithAdminUser() {
		assertNotNull(todoRepository.findAll());
	}
	
	@Test
	@WithMockOAuth2User(scopes = "read", roles="ADMIN")
	public void findAllPagedWithAdminUser() {
		assertNotNull(todoRepository.findAll(new PageRequest(0,1)));
	}
	
	@Test
	@WithAnonymousUser
	public void findAllPublicUnauthenticated() {
		assertNotNull(todoRepository.findAllNonSecret(new PageRequest(0, 1)));
	}
	
}

