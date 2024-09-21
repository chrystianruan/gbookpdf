package com.api.gbookpdf.dtos;

import com.api.gbookpdf.entities.Role;
import com.api.gbookpdf.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter @Setter
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String telephone;
    private Date createdAt;
    private Date updatedAt;

    public User parseToObject() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setTelephone(telephone);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return user;
    }
}
