package com.api.gbookpdf.services;

import com.api.gbookpdf.dtos.UserDTO;
import com.api.gbookpdf.entities.User;
import com.api.gbookpdf.enums.RoleEnum;
import com.api.gbookpdf.repositories.RoleRepository;
import com.api.gbookpdf.repositories.UserRepository;
import com.api.gbookpdf.utils.ResponseUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Map<String, String> saveUser(UserDTO userDTO) {
        try {
            if (userRepository.findByEmailOrTelephone(userDTO.getEmail(), userDTO.getTelephone()) != null) {
                return ResponseUtils.makeMessageWithCode("Usuário já existe no sistema", Integer.toString(HttpStatus.FORBIDDEN.value()));
            }
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            user.setTelephone(userDTO.getTelephone());
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setActive(true);
            user.setRole(roleRepository.findOne(RoleEnum.ADMIN.getValue()));

            userRepository.save(user);

            return ResponseUtils.makeMessageWithCode("Usuário cadastrado com sucesso", Integer.toString(HttpStatus.ACCEPTED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
