package com.br.ages.orientacaobucalbackend.Controllers;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.UserRepository;
import com.br.ages.orientacaobucalbackend.Entity.Dto.UserDto;
import com.br.ages.orientacaobucalbackend.Entity.User;
import com.br.ages.orientacaobucalbackend.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = UserDto.listToDto(users);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> newUser(@RequestBody User user) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
            userDto.setRole(user.getRole());
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new UserException("An Error occurred while creating this user.");
        }
    }
}
