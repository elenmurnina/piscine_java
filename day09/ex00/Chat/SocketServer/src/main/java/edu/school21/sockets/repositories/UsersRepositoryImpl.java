package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
    public static final RowMapper<User> USER_ROW_MAPPER = (rs, num) -> new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password")
    );
    private final JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            return null;
        } else {
            return jdbcTemplate.query("SELECT * FROM chat.users WHERE id=?", USER_ROW_MAPPER,
                    id).stream().findFirst().orElse(null);
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat.users ORDER BY id", USER_ROW_MAPPER);
    }

    @Override
    public void save(User user) {
        if (user != null) {
            Long id = jdbcTemplate.query("INSERT INTO chat.users (username, password) VALUES (?, ?) RETURNING id",
                    (rs, num) -> rs.getLong(1),
                    user.getUsername(), passwordEncoder.encode(user.getPassword())).stream().findFirst().orElse(null);
            user.setId(id);
        }
    }

    @Override
    public void update(User user) {
        if (user != null) {
            jdbcTemplate.update("UPDATE chat.users SET username=?, password=? WHERE id=?",
                    user.getUsername(), user.getPassword(), user.getId());
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            jdbcTemplate.update("DELETE FROM chat.users WHERE id=?", id);
        }
    }
}
