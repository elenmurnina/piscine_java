package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class ProductsRepositoryJdbcImplTest {
    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = createProductList();
    private final Product SAVE_PRODUCT = new Product(7L, "Camembert", 66);

    public EmbeddedDatabase embeddedDataBase;
    private ProductsRepository productsRepository;

    @BeforeEach
    public void init() {
        embeddedDataBase = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("/schema.sql", "/data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(embeddedDataBase);
    }

    @AfterEach
    public void after() {
        embeddedDataBase.shutdown();
    }

    private List<Product> createProductList() {
        List<Product> lst = new ArrayList<>();
        Product product1 = new Product(1L, "Bread", 10);
        Product product2 = new Product(2L, "Oil", 25);
        Product product3 = new Product(3L, "Dress", 1042);
        Product product4 = new Product(4L, "Sugar", 30);
        Product product5 = new Product(5L, "Cheese", 42);
        Product product6 = new Product(6L, "Chocolate", 21);
        lst.add(product1);
        lst.add(product2);
        lst.add(product3);
        lst.add(product4);
        lst.add(product5);
        lst.add(product6);
        return lst;
    }

    @Test
    public void testFindAllProduct() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    public void testFindByIdProduct() throws SQLException {
        for (Product product : EXPECTED_FIND_ALL_PRODUCTS) {
            Assertions.assertEquals(product, productsRepository.findById(product.getId()).orElse(null));
        }
        Assertions.assertFalse(productsRepository.findById(55L).isPresent());

    }

    @Test
    public void testUpdateProduct() throws SQLException {
        Product product = new Product(5L, null, 0);
        product.setName("Camembert");
        product.setPrice(66);
        productsRepository.update(product);
        Assertions.assertEquals(product, productsRepository.findById(5L).orElse(null));
        Assertions.assertEquals("Camembert", productsRepository.findById(5L).orElse(null).getName());
        Assertions.assertEquals(66, productsRepository.findById(5L).orElse(null).getPrice());
    }

    @Test
    public void testSaveProduct() throws SQLException {
        List<Product> lst = new ArrayList<>(EXPECTED_FIND_ALL_PRODUCTS);
        lst.add(SAVE_PRODUCT);
        productsRepository.save(SAVE_PRODUCT);
        Assertions.assertEquals(lst, productsRepository.findAll());
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        List<Product> lst = new ArrayList<>(EXPECTED_FIND_ALL_PRODUCTS);
        lst.remove(0);
        productsRepository.delete(1L);
        Assertions.assertEquals(lst, productsRepository.findAll());
    }
}
