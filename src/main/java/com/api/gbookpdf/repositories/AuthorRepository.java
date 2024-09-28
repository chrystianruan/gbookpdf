package com.api.gbookpdf.repositories;

import com.api.gbookpdf.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a where a.id = ?1")
    Author findOne(Long id);
}
