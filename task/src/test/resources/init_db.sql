CREATE TABLE author ( id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL,
                      surname VARCHAR(255), middle_name VARCHAR(255),
                      date_of_birth DATE, creation_date TIMESTAMP,
                      update_date TIMESTAMP);

CREATE TABLE book ( id SERIAL PRIMARY KEY, isbn VARCHAR(255) NOT NULL,
                    creation_date TIMESTAMP, update_date TIMESTAMP);

CREATE TABLE genre ( id SERIAL PRIMARY KEY, description VARCHAR(255),
                     creation_date TIMESTAMP, update_date TIMESTAMP);

CREATE TABLE author_book ( book_id INTEGER REFERENCES book (id),
                           author_id INTEGER REFERENCES author (id));

CREATE TABLE book_genre (book_id INTEGER REFERENCES book (id),
                         genre_id INTEGER REFERENCES genre (id));

insert into author (name, surname, middle_name, date_of_birth, creation_date, update_date)
values ('Василий', 'Васильев', 'Васильевич', '1999-01-08', current_timestamp, current_timestamp),
       ('Василий', 'Петров', 'Васильевич', '1999-01-08', current_timestamp, current_timestamp),
       ('Василий', 'Иванов', 'Васильевич', '1999-01-08', current_timestamp, current_timestamp);

insert into genre (description, creation_date, update_date)
values ('Фантастика', current_timestamp, current_timestamp),
       ('Детектив', current_timestamp, current_timestamp),
       ('Роман', current_timestamp, current_timestamp),
       ('Драма', current_timestamp, current_timestamp);

insert into book (isbn, creation_date, update_date)
values ('978-5-25841-135-1', current_timestamp, current_timestamp),
       ('978-5-26312-423-8', current_timestamp, current_timestamp),
       ('978-5-31787-134-1', current_timestamp, current_timestamp),
       ('978-5-90734-138-4', current_timestamp, current_timestamp);

insert into author_book(book_id, author_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 1),
       (1, 2);

insert into book_genre(book_id, genre_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (4, 2);