package com.api.gbookpdf.repositories;

import com.api.gbookpdf.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("select subject from Subject subject where subject.id = ?1")
    Subject findOne(Long id);

    @Query("select count(subject) > 1 from Subject subject where subject.name ilike concat('%', :name, '%')")
    boolean existsByName(String name);

    @Query("select count(subject) > 1 from Subject subject where subject.name ilike concat('%', :name, '%') and subject <> ?2")
    boolean existsByNameAndCurrentObjectDifferent(String name, Subject subject);
}
