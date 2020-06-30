CREATE TABLE company (
    id INT NOT NULL,
    name VARCHAR (100),
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person (
    id INT NOT NULL,
    name VARCHAR(100),
    company_id INT,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

1. Вывести имена всех лиц, которые не находятся в компании с id = 5, также названия компании для каждого человека
SELECT p.name, c.name FROM company AS c
INNER JOIN person AS p ON c.id = p.company_id
WHERE c.id <> 5;

2. Выберите название компании с максимальным количеством человек + количество людей в этой компании
SELECT c.name, COUNT(c.name) FROM company AS c
INNER JOIN person AS p ON c.id = p.company_id
GROUP BY c.name
ORDER BY count
DESC LIMIT 1;
