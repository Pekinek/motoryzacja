package backend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.exceptions.UnauthorizedException;
import backend.model.Offer;
import backend.model.User;
import backend.repository.OfferRepository;
import backend.repository.UserRepository;

@RestController
@CrossOrigin
public class OfferController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OfferRepository offerRepository;

	@RequestMapping("/offers/upload")
	public ResponseEntity<String> addOffer(@RequestHeader("Authorization") String token, @RequestBody Offer offer)
			throws UnauthorizedException {
		User user = userRepository.findByToken(token);
		if (user.equals(null)) {
			throw new UnauthorizedException();
		}
		offer.setUser(user);
		offer.setDate(new Date().getTime());
		offer.setArchived(false);
		offerRepository.save(offer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping("/offers/all")
	public ResponseEntity<PagedResources<Resource<Offer>>> getAll(@RequestParam("title") String title,
			@RequestParam("type") String type, @RequestParam("place") String place,
			@RequestParam("minPrice") Double minPrice, @RequestParam("maxPrice") Double maxPrice, Pageable pageable,
			PagedResourcesAssembler<Offer> assembler) {

		if (minPrice == null)
			minPrice = 0.0;
		if (maxPrice == null)
			maxPrice = 999999999.0;

		Page<Offer> offers = offerRepository.findByFilter(title, type, place, minPrice, maxPrice, pageable);
		return new ResponseEntity<>(assembler.toResource(offers), HttpStatus.OK);
	}

	@RequestMapping("/offers/{id}")
	public ResponseEntity<Offer> getOfferById(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(offerRepository.findOne(id), HttpStatus.OK);
	}

	@RequestMapping("/offers/my")
	public ResponseEntity<PagedResources<Resource<Offer>>> getUserOffers(@RequestHeader("Authorization") String token, @RequestParam("title") String title,
			@RequestParam("type") String type, @RequestParam("place") String place,
			@RequestParam("minPrice") Double minPrice, @RequestParam("maxPrice") Double maxPrice, Pageable pageable, PagedResourcesAssembler<Offer> assembler) {
		
		if (minPrice == null)
			minPrice = 0.0;
		if (maxPrice == null)
			maxPrice = 999999999.0;
		String login = userRepository.findByToken(token).getLogin();
		Page<Offer> offers = offerRepository.findByFilterAndUser(title, type, place, minPrice, maxPrice, login, pageable);
		return new ResponseEntity<>(assembler.toResource(offers), HttpStatus.OK);
	}

	@RequestMapping(method = { RequestMethod.DELETE }, value = { "/offers/delete/{id}" })
	public ResponseEntity<Iterable<Offer>> removeOffer(@RequestHeader("Authorization") String token,
			@PathVariable Integer id) throws UnauthorizedException {
		Offer offer = offerRepository.findOne(id);

		if (offer.getUser().getToken().equals(token)) {
			List<User> users = (List<User>) userRepository.findAll();
			offerRepository.delete(offer);
		} else {
			throw new UnauthorizedException();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
