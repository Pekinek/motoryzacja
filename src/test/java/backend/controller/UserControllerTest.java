package backend.controller;

import backend.exceptions.UnauthorizedException;
import backend.model.User;
import backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UserControllerTest extends UserController{

    @Before
    public void init(){
        userRepository = mock(UserRepository.class);
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldThrowExceptionWhenTokenIsWrong() throws UnauthorizedException {
        when(userRepository.findByToken("123")).thenReturn(null);
        changePassword("123", "abc");
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldThrowExceptionWhenTokenIsWrong2() throws UnauthorizedException {
        when(userRepository.findByToken("123")).thenReturn(null);
        changePassword("123", new User());
    }

    @Test
    public void shouldSaveUserWithNewPassword() throws UnauthorizedException {
        User user = mock(User.class);
        when(userRepository.findByToken("123")).thenReturn(user);

        changePassword("123", "abc");

        verify(user).setPassword("abc");
        verify(userRepository).save(user);
    }

    @Test
    public void shouldSaveUserWithNewData() throws UnauthorizedException {
        User user = mock(User.class);
        when(userRepository.findByToken("123")).thenReturn(user);
        User newUser = new User();
        newUser.setFirstName("Jan");
        newUser.setLastName("Kowalski");
        newUser.setEmail("jan@gmail.com");
        newUser.setTelephone("123456789");

        changePassword("123", newUser);

        verify(user).setFirstName("Jan");
        verify(user).setLastName("Kowalski");
        verify(user).setEmail("jan@gmail.com");
        verify(user).setTelephone("123456789");
        verify(userRepository).save(user);
    }
}
