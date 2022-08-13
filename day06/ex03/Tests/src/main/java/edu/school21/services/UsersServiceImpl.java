package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

@SuppressWarnings("unused")
public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        User user;
        try {
            user = usersRepository.findByLogin(login);
        } catch (UsersRepository.EntityNotFoundException ignored) {
            return false;
        }
        if (user.isAuth()) {
            throw new AlreadyAuthenticatedException("User with login " + login +
                    " already authenticated.");
        }
        if (user.getPassword().equals(password)) {
            user.setAuth(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}
