package backend.controller;

import backend.exceptions.InvalidPasswordException;
import backend.exceptions.UserDisabledException;
import backend.exceptions.UserNotFoundException;
import backend.model.Token;
import backend.model.User;
import backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class LoginControllerTest extends LoginController {

    @Before
    public void init(){
        userRepository = mock(UserRepository.class);
    }

    @Test(expected = UserNotFoundException.class)
    public void whenUserIsNotFoundExceptionShouldBeThrown() throws UserNotFoundException, UserDisabledException, InvalidPasswordException {
        when(userRepository.findByLogin("User1")).thenReturn(null);
        User userData = new User();
        userData.setLogin("User1");

        login(userData);
    }

    @Test(expected = InvalidPasswordException.class)
    public void whenPasswordIsIncorrectExceptionShouldBeThrown() throws UserNotFoundException, UserDisabledException, InvalidPasswordException {
        User user = new User();
        user.setPassword("qwerty");
        when(userRepository.findByLogin("User1")).thenReturn(user);
        User userData = new User();
        userData.setLogin("User1");
        userData.setPassword("abcdef");

        login(userData);
    }

    @Test(expected = UserDisabledException.class)
    public void whenUserIsDisabledExceptionShouldBeThrown() throws UserNotFoundException, UserDisabledException, InvalidPasswordException {
        User user = new User();
        user.setPassword("qwerty");
        user.setEnabled(false);
        when(userRepository.findByLogin("User1")).thenReturn(user);
        User userData = new User();
        userData.setLogin("User1");
        userData.setPassword("qwerty");

        login(userData);
    }

    @Test
    public void shouldPassInOtherCases() throws UserNotFoundException, UserDisabledException, InvalidPasswordException{
        User user = new User();
        user.setPassword("qwerty");
        user.setToken("123123123");
        user.setEnabled(true);
        when(userRepository.findByLogin("User1")).thenReturn(user);
        User userData = new User();
        userData.setLogin("User1");
        userData.setPassword("qwerty");

        ResponseEntity<Token> response = login(userData);
        assertThat(response.getBody().getToken(), is("123123123"));
    }
}
