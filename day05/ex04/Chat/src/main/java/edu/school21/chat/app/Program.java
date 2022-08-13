package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(ds);
        List<User> users = usersRepository.findAll(2, 3);
        System.out.println(users.size());
        for (User user : users) {
            System.out.println(user);
        }
        ds.close();
    }
}
