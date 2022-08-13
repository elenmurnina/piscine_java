package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    private static final String LOGIN = "elen";
    private static final String PASSWORD = "pass";

    @Test
    public void testCorrectLoginAndPassword() {
        UsersRepository usersRepository = mock(UsersRepository.class);
        when(usersRepository.findByLogin(eq(LOGIN))).thenReturn(new User(1L, LOGIN, PASSWORD, false));
        doNothing().when(usersRepository).update(eq(new User(1L, LOGIN, PASSWORD, true)));
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        Assertions.assertTrue(usersService.authenticate(LOGIN, PASSWORD));
        verify(usersRepository, times(1)).findByLogin(LOGIN);
        verify(usersRepository, times(1)).update(new User(1L, LOGIN, PASSWORD, true));
    }

    @Test
    public void testIncorrectLogin() {
        UsersRepository usersRepository = mock(UsersRepository.class);
        when(usersRepository.findByLogin(eq(LOGIN))).thenThrow(new UsersRepository.EntityNotFoundException());
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        Assertions.assertFalse(usersService.authenticate(LOGIN, PASSWORD));
        verify(usersRepository, times(1)).findByLogin(LOGIN);
        verify(usersRepository, times(0)).update(any());
    }

    @Test
    public void testIncorrectPassword() {
        UsersRepository usersRepository = mock(UsersRepository.class);
        when(usersRepository.findByLogin(eq(LOGIN))).thenReturn(new User(1L, LOGIN, "_", false));
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        Assertions.assertFalse(usersService.authenticate(LOGIN, PASSWORD));
        verify(usersRepository, times(1)).findByLogin(LOGIN);
        verify(usersRepository, times(0)).update(any());
    }

    @Test
    public void testAlreadyAuthenticated() {
        UsersRepository usersRepository = mock(UsersRepository.class);
        when(usersRepository.findByLogin(eq(LOGIN))).thenReturn(new User(1L, LOGIN, PASSWORD, true));
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate(LOGIN, PASSWORD));
        verify(usersRepository, times(1)).findByLogin(LOGIN);
        verify(usersRepository, times(0)).update(any());
    }
}
