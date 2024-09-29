package com.example.webapidemo.mapper.db1;

import com.example.webapidemo.AbstractIntegrationTest;
import com.example.webapidemo.entity.Book;
import com.example.webapidemo.mapper.db1.BookMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookMapperIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private BookMapper bookMapper;

    @BeforeAll
    static void setUp() throws SQLException {
        System.out.println( "PostgreSQL container is running on port: " + ContainerPostgres.getMappedPort(5432) );

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