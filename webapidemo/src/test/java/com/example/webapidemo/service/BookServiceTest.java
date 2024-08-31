package com.example.webapidemo.service;

import com.example.webapidemo.entity.Book;
import com.example.webapidemo.mapper.db1.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks() {
        List<Book> expectedBooks = Arrays.asList(
                new Book(1L, "Book1", "Author1", 2021, "1234567890", 19.99),
                new Book(2L, "Book2", "Author2", 2022, "0987654321", 29.99)
        );
        when(bookMapper.getAllBooks()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAllBooks();

        assertEquals(expectedBooks, actualBooks);
        verify(bookMapper, times(1)).getAllBooks();
    }

    @Test
    void getBookById() {
        Long bookId = 1L;
        Book expectedBook = new Book(bookId, "Test Book", "Test Author", 2023, "1111111111", 24.99);
        when(bookMapper.getBookById(bookId)).thenReturn(expectedBook);

        Book actualBook = bookService.getBookById(bookId);

        assertEquals(expectedBook, actualBook);
        verify(bookMapper, times(1)).getBookById(bookId);
    }

    // Add more tests for addBook, updateBook, and deleteBook methods
}