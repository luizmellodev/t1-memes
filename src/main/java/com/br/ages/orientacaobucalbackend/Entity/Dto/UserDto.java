package com.br.ages.orientacaobucalbackend.Entity.Dto;

import com.br.ages.orientacaobucalbackend.Entity.User;
import com.br.ages.orientacaobucalbackend.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private Role role;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }

    public static List<UserDto> listToDto(List<User> userList) {
        return userList.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
