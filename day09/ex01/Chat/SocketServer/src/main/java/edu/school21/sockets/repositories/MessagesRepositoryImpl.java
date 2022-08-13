package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class MessagesRepositoryImpl implements MessagesRepository {

    @Autowired
    private UsersRepository usersRepository;

    public final RowMapper<Message> MESSAGE_ROW_MAPPER = (rs, num) -> new Message(
            rs.getLong("id"),
            usersRepository.findById(rs.getLong("author_id")),
            rs.getString("text"),
            rs.getObject("creation_time", LocalDateTime.class)
    );

    private final JdbcTemplate jdbcTemplate;

    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Message findById(Long id) {
        if (id == null) {
            return null;
        }

        return jdbcTemplate.query("SELECT * FROM chat.messages WHERE id=?", MESSAGE_ROW_MAPPER,
                id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat.messages ORDER BY id", MESSAGE_ROW_MAPPER);
    }

    @Override
    public void save(Message message) {
        if (message != null) {
            Long id = jdbcTemplate.query("INSERT INTO chat.messages (author_id, text, creation_time)" +
                            " VALUES (?, ?, ?) RETURNING id",
                    (rs, num) -> rs.getLong(1),
                    message.getAuthor().getId(),
                    message.getText(),
                    message.getCreationTime()
            ).stream().findFirst().orElse(null);
            message.setId(id);
        }
    }

    @Override
    public void update(Message message) {
        if (message != null) {
            jdbcTemplate.update("UPDATE chat.messages SET author_id=?, text=?, creation_time=? WHERE id=?",
                    message.getAuthor().getId(),
                    message.getText(),
                    message.getCreationTime(),
                    message.getId());
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            jdbcTemplate.update("DELETE FROM chat.messages WHERE id=?", id);
        }
    }
}
