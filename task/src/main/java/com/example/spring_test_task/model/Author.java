package com.example.spring_test_task.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "author")
public class Author extends AbstractObject {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public static AuthorBuilder builder() {

        return new Author().new AuthorBuilder();
    }

    public class AuthorBuilder {

        private AuthorBuilder() {}

        public AuthorBuilder id(Long id) {
            Author.this.setId(id);
            return this;
        }

        public AuthorBuilder creationDate(LocalDateTime creationDate) {
            Author.this.setCreationDate(creationDate);
            return this;
        }

        public AuthorBuilder updateDate(LocalDateTime updateDate) {
            Author.this.setUpdateDate(updateDate);
            return this;
        }

        public AuthorBuilder name(String name) {
            Author.this.setName(name);
            return this;
        }

        public AuthorBuilder surname(String surname) {
            Author.this.setSurname(surname);
            return this;
        }

        public AuthorBuilder middleName(String middleName) {
            Author.this.setMiddleName(middleName);
            return this;
        }

        public AuthorBuilder dateOfBirth(LocalDate dateOfBirth) {
            Author.this.setDateOfBirth(dateOfBirth);
            return this;
        }

        public AuthorBuilder books(Set<Book> books) {
            Author.this.setBooks(books);
            return this;
        }

        public Author build() {
            return Author.this;
        }
    }
}
