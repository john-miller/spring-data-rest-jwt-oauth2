package io.essolutions.todo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	@PreAuthorize("hasRole('ADMIN')")
	@RestResource(exported=false)
	public List<Todo> findAll();
	
	@PreAuthorize("#oauth2.hasScope('read') AND hasRole('ADMIN')")
	@RestResource(exported=true)
	public Page<Todo> findAll(Pageable pageable);
	
	@PreAuthorize("permitAll()")
	@RestResource(exported=true)
	@Query("SELECT todo from Todo todo where todo.secret = false")
	public Page<Todo> findAllNonSecret(Pageable pageable);
	
	@PreAuthorize("(#oauth2.hasScope('read') AND hasPermission()) AND hasRole('USER')")
	@RestResource(exported=true)
	@Query("SELECT todo from Todo todo where todo.secret = true")
	public Page<Todo> findAllSecret(Pageable pageable);
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@RestResource(exported=true)
	@Override
	public void delete(Todo entity);

	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@PostFilter("hasPermission()")
	@RestResource(exported=true)
	@Override
	public <S extends Todo> S save(S entity);
		
}
