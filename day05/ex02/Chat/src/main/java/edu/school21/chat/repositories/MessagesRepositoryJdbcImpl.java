package edu.school21.chat.repositories;

import edu.school21.chat.exeptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
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

    @Override
    public void save(Message message) throws SQLException {
        final String QUERY = "INSERT INTO chat.messages (author, room, textmsg, datetime)" +
                " VALUES (?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            if (usersRepository.findLiteById(message.getAuthor().getId()).isPresent()
                    && chatroomsRepository.findLiteById(message.getRoom().getId()).isPresent()) {
                pStatement.setLong(1, message.getAuthor().getId());
                pStatement.setLong(2, message.getRoom().getId());
                pStatement.setString(3, message.getText());
                pStatement.setTimestamp(4, Timestamp.valueOf(message.getMessageDateTime()));
                pStatement.executeUpdate();
                try (ResultSet resultSet = pStatement.getGeneratedKeys()) {
                    resultSet.next();
                    message.setId(resultSet.getLong("id"));
                }
            } else {
                throw new NotSavedSubEntityException("Author and room have no ID existing" +
                        "in the database assigned, or these IDs are null.");
            }
        }
    }
}
