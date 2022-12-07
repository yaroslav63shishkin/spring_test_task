ALTER SEQUENCE author_id_seq RESTART;
ALTER SEQUENCE book_id_seq RESTART;
ALTER SEQUENCE genre_id_seq RESTART;

delete from book_genre where true;
delete from author_book where true;
delete from author where true;
delete from book where true;
delete from genre where true;

drop table book_genre;
drop table author_book;
drop table author;
drop table book;
drop table genre;