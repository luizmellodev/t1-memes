package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
