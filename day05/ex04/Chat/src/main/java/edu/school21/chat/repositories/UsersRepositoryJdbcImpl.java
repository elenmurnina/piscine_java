package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource ds;

    public UsersRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Optional<User> findLiteById(Long id) throws SQLException {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM chat.users WHERE id = " + id)) {
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(id, login, password, null, null);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll(int page, int size) throws SQLException {
        final String QUERY = "SELECT * FROM chat.users ORDER BY id LIMIT "
                + size + " OFFSET " + size * (page - 1);
        List<User> result = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(id, login, password, null, null);
                result.add(user);
            }
        }
        return result;
    }
}
