package com.api.gbookpdf.dtos;

import com.api.gbookpdf.entities.Role;
import com.api.gbookpdf.entities.User;
import com.api.gbookpdf.interfaces.IparseToObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Getter @Setter
@NoArgsConstructor
public class UserDTO implements IparseToObject {
    private String email;
    private String password;
    private String name;
    private String telephone;
    private Date createdAt;
    private Date updatedAt;

    @Override
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
