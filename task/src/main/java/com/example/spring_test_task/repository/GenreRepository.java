package com.example.spring_test_task.repository;

import com.example.spring_test_task.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {

    @Modifying
    @Query(value = "DELETE FROM book_genre WHERE genre_id = :id", nativeQuery = true)
    void deleteOrphan(@Param("id") Long id);
}
