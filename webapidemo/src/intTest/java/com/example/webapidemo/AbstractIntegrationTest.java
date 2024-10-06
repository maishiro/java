package com.example.webapidemo;

import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = WebapidemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
public class AbstractIntegrationTest {

    @LocalServerPort
    protected Integer port;

    @Container
    protected static final PostgreSQLContainer<?> ContainerPostgres = new PostgreSQLContainer<>( DockerImageName.parse("postgres:15") )
                                                                        .withExposedPorts(5432)
                                                                        .withDatabaseName("db1")
                                                                        .withUsername("postgres")
                                                                        .withPassword("postgres")
                                                                        .withFileSystemBind(
                                                                                "src/main/resources/mybatis/db1/init/init.sql",
                                                                                "/docker-entrypoint-initdb.d/01-schema.sql",
                                                                                BindMode.READ_ONLY)
                                                                        .withFileSystemBind(
                                                                                "src/main/resources/mybatis/db1/pg_hba.conf",
                                                                                "/etc/postgresql/pg_hba.conf",
                                                                                BindMode.READ_ONLY)
                                                                        ;

    static {
        ContainerPostgres.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add( "db1.datasource.jdbc-url",
                () -> String.format("jdbc:postgresql://localhost:%d/%s", ContainerPostgres.getMappedPort(5432), ContainerPostgres.getDatabaseName()) );
        registry.add("db1.datasource.username", ContainerPostgres::getUsername);
        registry.add("db1.datasource.password", ContainerPostgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI  = "http://localhost:" + port;
    }
}