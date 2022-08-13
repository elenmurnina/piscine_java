package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.*;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        long request = scanner.nextInt();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);
        System.out.println(messagesRepository.findById(request));

//        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(ds);
//        System.out.println(usersRepository.findLiteById(1L));
//        System.out.println(messagesRepository.findById(5L));
//        System.out.println(messagesRepository.findById(50L));
//        ChatroomsRepository chatroomsRepository = new ChatroomsRepositoryJdbcImpl(ds);
//        System.out.println(chatroomsRepository.findLiteById(4L));
        scanner.close();
        ds.close();
    }
}
