package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.Entity.Dto.UserLoginDto;
import com.br.ages.orientacaobucalbackend.DataAcess.Repository.UserRepository;
import com.br.ages.orientacaobucalbackend.Entity.User;
import com.br.ages.orientacaobucalbackend.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
@CrossOrigin
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserLoginDto userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean login = false;
        Optional<User> user = userRepository.findByEmail(userDTO.getUsername());
        if (user.isPresent() && user.get().getRole() == Role.ADMIN) {
            String password = userDTO.getPassword();
            String dbpassword = user.get().getPassword();
            boolean matches = bCryptPasswordEncoder.matches(password, dbpassword);
            if (matches) {
                login = true;
            }
        }
        if (login) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }
}
