package com.api.gbookpdf.repositories;

import com.api.gbookpdf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailOrTelephone(String username, String telephone);
}
