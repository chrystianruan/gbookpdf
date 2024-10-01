package com.api.gbookpdf.controllers;

import com.api.gbookpdf.dtos.LoginDTO;
import com.api.gbookpdf.dtos.UserAuthDTO;
import com.api.gbookpdf.dtos.UserAuthSimpleDTO;
import com.api.gbookpdf.entities.User;
import com.api.gbookpdf.services.TokenService;
import com.api.gbookpdf.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    private final Logger log = Logger.getLogger(UserController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<Map<String, ?>> auth(@RequestBody LoginDTO loginDTO) {
        try {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        if (!auth.isAuthenticated()) {
            return ResponseEntity.status(401).body(ResponseUtils.makeMessage("Nome de usuário ou senha inválidos"));
        }
        User user = (User) auth.getPrincipal();
        String token = tokenService.createToken(user);

        UserAuthDTO userAuthDTO = new UserAuthDTO(new UserAuthSimpleDTO(user.getName(), user.getRole().getName()), token);

        return ResponseEntity.ok(ResponseUtils.makeMessageWithObject(userAuthDTO));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseUtils.makeMessage("Erro ao tentar fazer login. Cheque seu nome de usuário e senha!"));
        }
    }
 }
