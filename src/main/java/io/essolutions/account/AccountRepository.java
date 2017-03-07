package io.essolutions.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	@PreAuthorize("#oauth2.hasScope('read') AND hasRole('ADMIN')")
	@RestResource(exported=true)
	public Account findOne(long id);
	
	@PreAuthorize("#oauth2.hasScope('read') AND hasRole('ADMIN')")
	@RestResource(exported=true)
	public Account findOneByUsername(@Param("username") String username);
	
	@PreAuthorize("#oauth2.hasScope('write') AND hasAnyRole('USER', 'ADMIN')")
	@RestResource(exported=true)
	@Override
	public void delete(Account account);

	@PreAuthorize("#oauth2.hasScope('write') AND hasAnyRole('USER', 'ADMIN')")
	@RestResource(exported=true)
	@Override
	public <S extends Account> S save(S account);

}
