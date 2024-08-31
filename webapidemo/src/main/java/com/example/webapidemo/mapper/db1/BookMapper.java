package com.example.webapidemo.mapper.db1;

import com.example.webapidemo.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    int insertBook(Book book);
    void updateBook(Book book);
    void deleteBook(Long id);
}