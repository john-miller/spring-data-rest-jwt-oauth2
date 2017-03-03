package io.essolutions.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@RestResource(exported=true)
	public Page<Todo> findAll(Pageable pageable);
	
}
