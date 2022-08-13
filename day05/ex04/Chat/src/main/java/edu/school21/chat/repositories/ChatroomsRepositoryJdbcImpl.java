package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ChatroomsRepositoryJdbcImpl implements ChatroomsRepository {
    private final DataSource ds;

    public ChatroomsRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Optional<Chatroom> findLiteById(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM chat.rooms WHERE id = " + id)) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Chatroom chatroom = new Chatroom(id, name, null, null);
                return Optional.of(chatroom);
            }
            return Optional.empty();
        }
    }
}
