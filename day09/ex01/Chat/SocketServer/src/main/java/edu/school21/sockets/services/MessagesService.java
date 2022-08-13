package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface MessagesService {
    void publishMessage(User author, String text);
}
