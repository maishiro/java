package com.example.webapidemo;

import com.example.webapidemo.entity.Book;
import com.example.webapidemo.mapper.db1.BookMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = WebapidemoApplication.class)
@Testcontainers
public class BookIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withExposedPorts(5432)
            .withDatabaseName("db1")
            .withUsername("postgres")
            .withPassword("postgres")
            .withFileSystemBind(
                    "src/main/resources/mybatis/db1/init.sql",
                    "/docker-entrypoint-initdb.d/01-schema.sql",
                    BindMode.READ_ONLY)
            ;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add( "db1.datasource.jdbc-url",
                () -> String.format("jdbc:postgresql://localhost:%d/%s", postgres.getMappedPort(5432), postgres.getDatabaseName()) );
        registry.add("db1.datasource.username", postgres::getUsername);
        registry.add("db1.datasource.password", postgres::getPassword);
    }

    @Autowired
    private BookMapper bookMapper;

    @BeforeAll
    static void setUp() throws SQLException {
        postgres.start();

        System.out.println( "PostgreSQL container is running on port: " + postgres.getMappedPort(5432) );

        //postgreSQLContainer.execInContainer(wrapQuery("CREATE DATABASE test"));
        //postgreSQLContainer.execInContainer(wrapQuery("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(255));"));
        //postgreSQLContainer.execInContainer(wrapQuery("INSERT INTO users (id, name) VALUES (1, 'John Doe');"));
    }

    @Test
    void testInsertAndRetrieveBook() {
        List<Book> listBooks = bookMapper.getAllBooks();
        assertEquals( 3, listBooks.size() );

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        int affectedRows = bookMapper.insertBook(book);
        assertEquals(1, affectedRows);
        assertNotNull(book.getId());  // idが自動設定されていることを確認

        Book retrievedBook = bookMapper.getBookById(book.getId());
        assertNotNull(retrievedBook);
        assertEquals(book.getTitle(), retrievedBook.getTitle());
        assertEquals(book.getAuthor(), retrievedBook.getAuthor());
    }
}