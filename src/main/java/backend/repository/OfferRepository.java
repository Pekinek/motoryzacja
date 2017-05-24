package backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import backend.model.Offer;
import backend.model.User;

@RepositoryRestResource(exported = false)
public interface OfferRepository extends PagingAndSortingRepository<Offer, Integer> {

	@Query("select c from Offer c where c.title like %?1%" + " and c.type like %?2%" + " and c.place like %?3%"
			+ " and c.price >= ?4" + " and c.price <= ?5" + " and c.user.login like ?6")
	Page<Offer> findByFilterAndUser(@Param("title") String title, @Param("type") String type, @Param("place") String place,
			@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("login") String login, Pageable pageable);


	@Query("select c from Offer c where c.title like %?1%" + " and c.type like %?2%" + " and c.place like %?3%"
			+ " and c.price >= ?4" + " and c.price <= ?5")
	Page<Offer> findByFilter(@Param("title") String title, @Param("type") String type, @Param("place") String place,
			@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);

}
