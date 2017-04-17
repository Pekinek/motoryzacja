package backend.controller;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.exceptions.InvalidPhoneNumberException;
import backend.exceptions.UserEmailExistsException;
import backend.exceptions.UserLoginExistsException;
import backend.model.User;
import backend.repository.UserRepository;
import backend.service.EmailService;

@RestController
public class RegistrationController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@CrossOrigin
	@RequestMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user)
			throws UserLoginExistsException, UserEmailExistsException, InvalidPhoneNumberException {
		
		if (userRepository.findByLogin(user.getLogin()) != null) {
			throw new UserLoginExistsException();
		}
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserEmailExistsException();
		}
		if (!StringUtils.isNumeric(user.getTelephone())) {
			throw new InvalidPhoneNumberException();
		}
		user.setType("normal");
		user.setToken(UUID.randomUUID().toString());
		user.setEnabled(true);
		String password = RandomStringUtils.randomAlphanumeric(10);
		user.setPassword(DigestUtils.md5Hex(password));
		userRepository.save(user);
		emailService.sendEmail(user, password);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}