package backend.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.exceptions.UnauthorizedException;
import backend.model.Offer;
import backend.model.User;
import backend.repository.OfferRepository;
import backend.repository.UserRepository;

@RestController
@CrossOrigin
public class AdminController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OfferRepository offerRepository;

	@RequestMapping("/admin/users")
	public ResponseEntity<PagedResources<Resource<User>>> getUsers(@RequestHeader("Authorization") String token,
			Pageable pageable, PagedResourcesAssembler<User> assembler) throws UnauthorizedException {

		if (userRepository.findByToken(token).getType() == "admin") {
			throw new UnauthorizedException();
		}

		Page<User> users = userRepository.findByType("normal", pageable);
		return new ResponseEntity<>(assembler.toResource(users), HttpStatus.OK);
	}

	@RequestMapping("/admin/users/enable/{login}")
	public ResponseEntity<PagedResources<Resource<User>>> enableUser(@RequestHeader("Authorization") String token,
			@PathVariable("login") String login) throws UnauthorizedException {

		if (userRepository.findByToken(token).getType() == "admin") {
			throw new UnauthorizedException();
		}

		User user = userRepository.findByLogin(login);
		user.setEnabled(true);
		userRepository.save(user);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping("/admin/users/disable/{login}")
	public ResponseEntity<PagedResources<Resource<User>>> disableUser(@RequestHeader("Authorization") String token,
			@PathVariable("login") String login) throws UnauthorizedException {

		if (userRepository.findByToken(token).getType() == "admin") {
			throw new UnauthorizedException();
		}

		User user = userRepository.findByLogin(login);
		user.setEnabled(false);
		userRepository.save(user);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method = { RequestMethod.DELETE }, value = { "/admin/offers/delete/{id}" })
	public ResponseEntity<Iterable<Offer>> removeOffer(@RequestHeader("Authorization") String token,
			@PathVariable Integer id) throws UnauthorizedException {
		Offer offer = offerRepository.findOne(id);
		User user = userRepository.findByToken(token);

		if (user.getType().equals("admin")) {
			offerRepository.delete(offer);
		} else {
			throw new UnauthorizedException();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
