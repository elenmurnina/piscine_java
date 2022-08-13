package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            return null;
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeQuery("SELECT * FROM spring.users WHERE id=" + id);
            ResultSet resultSet = statement.getResultSet();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Can't find user by id [id=" + id + "].", e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeQuery("SELECT * FROM spring.users ORDER BY id");
            ResultSet resultSet = statement.getResultSet();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Can't find all users.", e);
        }
    }

    @Override
    public void save(User user) {
        if (user != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO spring.users (email) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getEmail());
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("User not saved [user=" + user + "].");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't save user " + user + ".", e);
            }
        }
    }

    @Override
    public void update(User user) {
        if (user != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE spring.users SET email=? WHERE id=?")) {
                statement.setString(1, user.getEmail());
                statement.setLong(2, user.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Can't update user " + user + ".", e);
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM spring.users WHERE id=" + id);
            } catch (SQLException e) {
                throw new RuntimeException("Can't delete [id=" + id + "].", e);
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) {
            return null;
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM spring.users WHERE email=?")) {

            statement.setString(1, email);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
            }
            return user == null ? Optional.empty() : Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException("Can't find user by email [email=" + email + "].", e);
        }
    }
}
