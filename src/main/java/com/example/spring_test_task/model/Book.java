package com.example.spring_test_task.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book extends AbstractObject {

    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    public Book(String isbn, Author author, Genre genre) {
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }

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
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
