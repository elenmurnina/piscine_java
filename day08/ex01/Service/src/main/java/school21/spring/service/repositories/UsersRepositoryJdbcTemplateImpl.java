package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl  implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName("spring").withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM spring.users WHERE id=?", (rs, num) -> new User(
                rs.getLong("id"),
                rs.getString("email")), id).stream().findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM spring.users ORDER BY id", (rs, num) -> new User(
                rs.getLong("id"),
                rs.getString("email")));
    }

    @Override
    public void save(User user) {
        if (user != null) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("email", user.getEmail());

            Number id = jdbcInsert.executeAndReturnKey(parameters);
            user.setId((Long) id);
        }
    }

    @Override
    public void update(User user) {
        if (user != null) {
            jdbcTemplate.update("UPDATE spring.users SET email=? WHERE id=?",
                    user.getEmail(), user.getId());
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            jdbcTemplate.update("DELETE FROM spring.users WHERE id=?", id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM spring.users WHERE email=?", (rs, num) -> new User(
                rs.getLong("id"),
                rs.getString("email")), email).stream().findFirst();
    }
}
