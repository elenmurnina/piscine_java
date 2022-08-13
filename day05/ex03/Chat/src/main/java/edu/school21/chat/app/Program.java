package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.PrintWriter;
import java.sql.SQLException;
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

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);
        Message message = messagesRepository.findById(4L).orElse(null);
        System.out.println(message);
        if (message != null) {
            message.setText("Hello, I'm new message.");
            message.setMessageDateTime(null);
            messagesRepository.update(message);
        }
        System.out.println(message);
        ds.close();
    }
}
