CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    link VARCHAR(500) UNIQUE,
    create_date DATE
);