package com.api.gbookpdf.controllers;

import com.api.gbookpdf.dtos.UserDTO;
import com.api.gbookpdf.repositories.UserRepository;
import com.api.gbookpdf.services.UserService;
import com.api.gbookpdf.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private final Logger log = Logger.getLogger(UserController.class.getName());

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> save(@RequestBody UserDTO userDTO) {
        try {
            Map<String, String> response = userService.saveUser(userDTO);
            if (response.get("code") != "202") {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            return ResponseEntity.accepted().body(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro interno"));
        }
    }
}
