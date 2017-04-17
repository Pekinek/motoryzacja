package backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.exceptions.InvalidPasswordException;
import backend.exceptions.UserDisabledException;
import backend.exceptions.UserNotFoundException;
import backend.model.Token;
import backend.model.User;
import backend.repository.UserRepository;

@RestController
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@CrossOrigin
	@RequestMapping("/login")
	public ResponseEntity<Token> login(@RequestBody User loginData)
			throws UserNotFoundException, InvalidPasswordException, UserDisabledException {
		User user = userRepository.findByLogin(loginData.getLogin());
		if (user == null) {
			throw new UserNotFoundException();
		}
		if (!user.getPassword().equals(loginData.getPassword())) {
			throw new InvalidPasswordException();
		}
		if (user.getEnabled() == false) {
			throw new UserDisabledException();
		}
		return new ResponseEntity<>(new Token(user.getToken(), user.getLogin(),
				user.getType()), HttpStatus.OK);
	}
}
