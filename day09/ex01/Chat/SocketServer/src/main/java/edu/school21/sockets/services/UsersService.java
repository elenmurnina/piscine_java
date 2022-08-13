package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface UsersService {
    void register(String userName, String password);

    User authenticate(String userName, String password);
}
