package backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import backend.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByLogin(@Param("login") String login);
    
    User findByEmail(@Param("email") String email);

    User findByToken(@Param("token") String token);

	Page<User> findByType(@Param("type") String type, Pageable pageable);
}