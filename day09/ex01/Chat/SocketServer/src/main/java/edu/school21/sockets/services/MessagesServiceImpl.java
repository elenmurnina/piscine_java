package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public void publishMessage(User author, String text) {
        messagesRepository.save(new Message(null, author, text, LocalDateTime.now()));
    }
}
