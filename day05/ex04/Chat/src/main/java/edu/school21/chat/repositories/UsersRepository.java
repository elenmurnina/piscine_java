package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<User> findLiteById(Long id) throws SQLException;
    List<User> findAll(int page, int size) throws SQLException;
}
