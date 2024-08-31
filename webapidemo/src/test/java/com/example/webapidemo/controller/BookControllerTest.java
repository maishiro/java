package com.example.webapidemo.controller;

import com.example.webapidemo.entity.Book;
import com.example.webapidemo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

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
        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookController.getAllBooks();

        assertEquals(expectedBooks, actualBooks);
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById() {
        Long bookId = 1L;
        Book expectedBook = new Book(bookId, "Test Book", "Test Author", 2023, "1111111111", 24.99);
        when(bookService.getBookById(bookId)).thenReturn(expectedBook);

        ResponseEntity<Book> response = bookController.getBookById(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBook, response.getBody());
        verify(bookService, times(1)).getBookById(bookId);
    }

    // Add more tests for addBook, updateBook, and deleteBook methods
}