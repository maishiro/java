package com.example.webapidemo.controller;

import com.example.webapidemo.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class BookControllerIT extends AbstractIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllBooks() throws JsonProcessingException {
        String fooResource = "/api/books";
        String strResult = restTemplate.getForObject( fooResource, String.class );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree( strResult );
        assertThat( jsonNode.size() ).isEqualTo( 3 );
    }

    @Test
    void getIdBook() throws JsonProcessingException {
        String fooResource = "/api/books/1";
        String strResult = restTemplate.getForObject( fooResource, String.class );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree( strResult );
        assertThat( jsonNode.get("title").asText() ).isEqualTo( "The Great Gatsby" );
        assertThat( jsonNode.get("isbn").asText() ).isEqualTo( "9780743273565" );
    }
}
