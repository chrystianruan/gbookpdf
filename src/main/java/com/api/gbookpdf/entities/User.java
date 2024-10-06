package com.api.gbookpdf.entities;

import com.api.gbookpdf.dtos.UserDTO;
import com.api.gbookpdf.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter @Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String telephone;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Book> books;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role.getId() == RoleEnum.MASTER.getValue()) {
            return List.of(new SimpleGrantedAuthority(role.getName()), new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("GENERAL"));
        } else if (this.role.getId() == RoleEnum.ADMIN.getValue()) {
            return List.of(new SimpleGrantedAuthority(role.getName()), new SimpleGrantedAuthority("GENERAL"));
        } else {
            return List.of(new SimpleGrantedAuthority(role.getName()));
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public UserDTO parseToDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(this.email);
        userDTO.setName(this.name);
        userDTO.setTelephone(this.telephone);
        userDTO.setCreatedAt(createdAt);
        userDTO.setUpdatedAt(updatedAt);

        return userDTO;
    }
}
