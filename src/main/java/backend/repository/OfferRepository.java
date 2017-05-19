package backend.repository;

import backend.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OfferRepository extends PagingAndSortingRepository<Offer, Integer> {
    Page<Offer> findByUser(@Param("login") String login, Pageable pageable);

}
