INSERT INTO executed_queries (query, is_schema, executed_at)
VALUES
    ('SELECT * FROM companies;', false, '2020-01-01 00:00:00'),
    ('SELECT * FROM products;', false, '2020-01-02 00:00:00'),
    ('CREATE TABLE companies (id INT PRIMARY KEY, name VARCHAR(255), description TEXT, created_at TIMESTAMP, updated_at TIMESTAMP);', true, '2020-01-03 00:00:00');
