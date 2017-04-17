package backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import backend.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByLogin(String login);
    
    User findByEmail(String email);

}