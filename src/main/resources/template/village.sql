CREATE DATABASE village;

CREATE TABLE citizen (id SERIAL PRIMARY KEY, name varchar(60) NOT NULL, lastname varchar(60) NOT NULL, CPF varchar(15) NOT NULL UNIQUE, income decimal(10,2) NOT NULL, expense decimal(10,2) NOT NULL, dataNascimento Date NOT NULL);

INSERT INTO citizen VALUES (DEFAULT, 'Rafael', 'Vilvert', '66655588877', 1500.00, 978.00, '1990/10/07');
INSERT INTO citizen VALUES (DEFAULT, 'Bruna', 'Vilvert', '12365498778', 4500.00, 2578.00, '1995/07/07');

CREATE TABLE "user" (id SERIAL PRIMARY KEY, email VARCHAR(100) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, citizen_id BIGINT, CONSTRAINT user_table_citizen_fk FOREIGN KEY (citizen_id) REFERENCES citizen (id));

INSERT INTO "user" (email, password, citizen_id) VALUES ('rafavilvert@gmail.com', '$2a$10$NiT2fCKsyKjxjqgu9g9UsOp9qoIh7IstFkJaDaHY6A9zhK5Kxirhq', 1);
INSERT INTO "user" (email, password, citizen_id) VALUES ('brunavilvert@gmail.com', '$2a$10$NiT2fCKsyKjxjqgu9g9UsOp9qoIh7IstFkJaDaHY6A9zhK5Kxirhq', 2);

**Run the project and then:

INSERT INTO user_roles (user_id, roles) VALUES(1, 'USER, ADMIN');
INSERT INTO user_roles (user_id, roles) VALUES(2, 'USER, ADMIN');
