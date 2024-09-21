package com.api.gbookpdf.repositories;

import com.api.gbookpdf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByEmailOrTelephone(String username, String telephone);
}
