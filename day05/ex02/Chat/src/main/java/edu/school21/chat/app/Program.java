package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class Program {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();

        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "postgres");
        props.setProperty("dataSource.databaseName", "postgres");
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);
        HikariDataSource ds = new HikariDataSource(config);

        UsersRepository ur = new UsersRepositoryJdbcImpl(ds);
        Optional<User> author = ur.findLiteById(1L);
        ChatroomsRepository cr = new ChatroomsRepositoryJdbcImpl(ds);
        Optional<Chatroom> chatroom = cr.findLiteById(2L);

        Message message = new Message(null, author.orElse(null),
                chatroom.orElse(null), "Hello!", LocalDateTime.now());
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);
        messagesRepository.save(message);
        System.out.println(message);
        System.out.println(message.getId());


        User creator = new User(null, "user", "user", new ArrayList(), new ArrayList());
        Message message2 = new Message(null, creator,
                chatroom.orElse(null), "Hi, World!", LocalDateTime.now());
        messagesRepository.save(message2);
        System.out.println(message);
        
        ds.close();
    }
}
