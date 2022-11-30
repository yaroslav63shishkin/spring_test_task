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
@Table(name = "genre")
public class Genre extends AbstractObject {

    @Column
    private String description;

    @OneToOne(mappedBy = "genre", fetch = FetchType.LAZY) // fixme: должен быть MtM
    private Book book;

    public Genre(String description, Book book) {
        this.description = description;
        this.book = book;
    }

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
}
