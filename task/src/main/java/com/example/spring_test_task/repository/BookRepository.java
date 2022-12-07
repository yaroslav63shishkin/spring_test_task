package com.example.spring_test_task.repository;

import com.example.spring_test_task.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query(value = "DELETE FROM author_book WHERE book_id = :id", nativeQuery = true)
    void deleteOrphan(@Param("id") Long id);
}
