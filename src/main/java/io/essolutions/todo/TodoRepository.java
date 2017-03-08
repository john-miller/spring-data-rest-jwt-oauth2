package io.essolutions.todo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface TodoRepository extends JpaRepository<Todo, Long> {

	/**
	 * 1. Allow clients with read access and users with the role users OR allow users with role admin.
	 * 2. After the request has been made, confirm that the user is the owner of the return object. Returns 
	 * a 403 if the requirements are not made.
	 */
	@PreAuthorize("(#oauth2.hasScope('read') AND hasRole('USER')) OR hasRole('ADMIN')")
	@PostAuthorize("(returnObject != null && returnObject.owner.username == authentication.name) OR hasRole('ADMIN')")
	public Todo findOne(Long id);
	
	/**
	 * Only authorize clients with read access and users with role admin
	 */
	@PreAuthorize("#oauth2.hasScope('read') AND hasRole('ADMIN')")
	@RestResource(exported=false)
	public List<Todo> findAll();
	
	@PreAuthorize("#oauth2.hasScope('read') AND hasRole('ADMIN')")
	@RestResource(exported=true)
	public Page<Todo> findAll(Pageable pageable);
	
	@PreAuthorize("permitAll()")
	@RestResource(exported=true)
	@Query("SELECT todo from Todo todo where todo.secret = false")
	public Page<Todo> findAllNonSecret(Pageable pageable);
	
	@PreAuthorize("#oauth2.hasScope('read') AND hasRole('USER')")
	@RestResource(exported=true)
	@Query("SELECT todo from Todo todo where todo.secret = true")
	public Page<Todo> findAllSecret(Pageable pageable);
	
	@PreAuthorize("(#oauth2.hasScope('write') AND hasRole('USER') AND #todo.owner.username == authentication.name) OR hasRole('ADMIN')")
	@RestResource(exported=true)
	@Override
	public void delete(@Param("todo") Todo entity);

	@PreAuthorize("(#oauth2.hasScope('write') AND hasRole('USER') AND #todo.owner.username == authentication.name) OR hasRole('ADMIN')")
	@RestResource(exported=true)
	@Override
	public <S extends Todo> S save(@Param("todo") S entity);
		
}
