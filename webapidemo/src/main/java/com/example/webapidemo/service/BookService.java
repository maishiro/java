package com.example.webapidemo.service;

import com.example.webapidemo.entity.Book;
import com.example.webapidemo.mapper.db1.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public List<Book> getAllBooks() {
        return bookMapper.getAllBooks();
    }

    public Book getBookById(Long id) {
        return bookMapper.getBookById(id);
    }

    public void addBook(Book book) {
        bookMapper.insertBook(book);
    }

    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    public void deleteBook(Long id) {
        bookMapper.deleteBook(id);
    }
}