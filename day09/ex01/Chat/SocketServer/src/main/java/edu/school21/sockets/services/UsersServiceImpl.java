package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(String userName, String password) {
        User user = new User(null, userName, password);
        usersRepository.save(user);
    }

    @Override
    public User authenticate(String userName, String password) {
        User user = usersRepository.findByUserName(userName);

        if (user == null) {
            return null;
        }

        if (password == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }
}
