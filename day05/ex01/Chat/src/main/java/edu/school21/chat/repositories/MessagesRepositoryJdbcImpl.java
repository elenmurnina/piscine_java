package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource ds;

    private final UsersRepository usersRepository;
    private final ChatroomsRepository chatroomsRepository;

    public MessagesRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
        usersRepository = new UsersRepositoryJdbcImpl(ds);
        chatroomsRepository = new ChatroomsRepositoryJdbcImpl(ds);
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM chat.messages WHERE id = " + id)) {
            if (resultSet.next()) {
                String text = resultSet.getString("textmsg");
                User author = usersRepository.findLiteById(resultSet.getLong(
                        "author")).orElse(null);
                Chatroom chatroom = chatroomsRepository.findLiteById(resultSet.getLong(
                        "room")).orElse(null);
                LocalDateTime messageDateTime = resultSet.getObject("datetime", LocalDateTime.class);
                Message message = new Message(id, author, chatroom, text, messageDateTime);
                return Optional.of(message);
            }
            return Optional.empty();
        }
    }
}
