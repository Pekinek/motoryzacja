package backend.repository;

import backend.model.Offer;
import backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OfferRepository extends PagingAndSortingRepository<Offer, Integer> {
    Page<Offer> findAll(@Param("login") String login, Pageable pageable);

    Page<Offer> findByUser(@Param("user") User user, Pageable pageable);

}
