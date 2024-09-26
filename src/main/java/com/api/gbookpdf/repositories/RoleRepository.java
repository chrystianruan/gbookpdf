package com.api.gbookpdf.repositories;

import com.api.gbookpdf.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select role from Role role where role.id = ?1")
    Role findOne(Integer id);
}
