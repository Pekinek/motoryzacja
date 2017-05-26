package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.exceptions.UnauthorizedException;
import backend.model.User;
import backend.repository.UserRepository;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/user/password")
	public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token,
			@RequestBody String password) throws UnauthorizedException {
		User user = userRepository.findByToken(token);
		if (user == null) {
			throw new UnauthorizedException();
		}
		user.setPassword(password);
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping("/user/contact")
	public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String token, @RequestBody User user)
			throws UnauthorizedException {
		User oldUser = userRepository.findByToken(token);
		if (user == null) {
			throw new UnauthorizedException();
		}
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		oldUser.setEmail(user.getEmail());
		oldUser.setTelephone(user.getTelephone());

		userRepository.save(oldUser);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
