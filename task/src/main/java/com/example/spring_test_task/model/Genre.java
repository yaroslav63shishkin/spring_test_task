package com.example.spring_test_task.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre extends AbstractObject {

    @Column
    private String description;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(getId(), genre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + getId() + '\'' +
                "description='" + description + '\'' +
                '}';
    }

    public static GenreBuilder builder() {

        return new Genre().new GenreBuilder();
    }

    public class GenreBuilder {

        private GenreBuilder() {}

        public GenreBuilder id(Long id) {
            Genre.this.setId(id);
            return this;
        }

        public GenreBuilder creationDate(LocalDateTime creationDate) {
            Genre.this.setCreationDate(creationDate);
            return this;
        }

        public GenreBuilder updateDate(LocalDateTime updateDate) {
            Genre.this.setUpdateDate(updateDate);
            return this;
        }

        public GenreBuilder description(String description) {
            Genre.this.setDescription(description);
            return this;
        }

        public GenreBuilder books(Set<Book> books) {
            Genre.this.setBooks(books);
            return this;
        }

        public Genre build() {
            return Genre.this;
        }
    }
}
