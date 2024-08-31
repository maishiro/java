CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INTEGER,
    isbn VARCHAR(13) UNIQUE,
    price DECIMAL(10, 2)
);

-- サンプルデータの挿入
INSERT INTO books (title, author, publication_year, isbn, price) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, '9780743273565', 12.99),
('To Kill a Mockingbird', 'Harper Lee', 1960, '9780446310789', 14.99),
('1984', 'George Orwell', 1949, '9780451524935', 11.99);
