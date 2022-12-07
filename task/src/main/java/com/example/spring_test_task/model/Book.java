package com.example.spring_test_task.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book extends AbstractObject {

    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + getId() + '\'' +
                "isbn='" + isbn + '\'' +
                '}';
    }

    public static BookBuilder builder() {

        return new Book().new BookBuilder();
    }

    public class BookBuilder {

        private BookBuilder() {}

        public BookBuilder id(Long id) {
            Book.this.setId(id);
            return this;
        }

        public BookBuilder creationDate(LocalDateTime creationDate) {
            Book.this.setCreationDate(creationDate);
            return this;
        }

        public BookBuilder updateDate(LocalDateTime updateDate) {
            Book.this.setUpdateDate(updateDate);
            return this;
        }

        public BookBuilder isbn(String isbn) {
            Book.this.setIsbn(isbn);
            return this;
        }

        public BookBuilder authors(Set<Author> authors) {
            Book.this.setAuthors(authors);
            return this;
        }

        public BookBuilder genres(Set<Genre> genres) {
            Book.this.setGenres(genres);
            return this;
        }

        public Book build() {
            return Book.this;
        }
    }
}
