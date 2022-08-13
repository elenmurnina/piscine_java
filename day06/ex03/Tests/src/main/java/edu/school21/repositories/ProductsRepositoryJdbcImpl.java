package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        final String QUERY = "SELECT * FROM product.product ORDER BY id";

        List<Product> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                result.add(new Product(id, name, price));
            }
        }
        return result;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM product.product WHERE id = " + id)) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                return Optional.of(new Product(id, name, price));
            }
            return Optional.empty();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        final String QUERY = "UPDATE product.product SET name = ?, price = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(QUERY)) {
            pStatement.setString(1, product.getName());
            pStatement.setInt(2, product.getPrice());
            pStatement.setLong(3, product.getId());
            pStatement.executeUpdate();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        final String QUERY = "INSERT INTO product.product (name, price) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pStatement.setString(1, product.getName());
            pStatement.setInt(2, product.getPrice());
            pStatement.executeUpdate();
            try (ResultSet resultSet = pStatement.getGeneratedKeys()) {
                resultSet.next();
                product.setId(resultSet.getLong("id"));
            }
        }
    }

    @Override
    public void delete(Long id)  throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM product.product WHERE id = " + id);
        }
    }
}
