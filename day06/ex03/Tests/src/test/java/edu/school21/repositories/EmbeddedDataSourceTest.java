package edu.school21.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDataSourceTest {

    private EmbeddedDatabase embeddedDataBase;

    @BeforeEach
    public void init() {
        embeddedDataBase = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("/schema.sql", "/data.sql")
                .build();
    }

    @AfterEach
    public void after() {
        embeddedDataBase.shutdown();
    }

    @Test
    public void testConnection() throws SQLException {
        try (Connection connection = embeddedDataBase.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1 + 1")) {
            Assertions.assertTrue(resultSet.next());
            Assertions.assertEquals(2, resultSet.getInt(1));
        }
    }
}
