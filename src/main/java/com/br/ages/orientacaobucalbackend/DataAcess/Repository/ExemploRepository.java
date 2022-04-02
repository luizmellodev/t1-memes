package com.br.ages.orientacaobucalbackend.DataAcess.Repository;

import com.br.ages.orientacaobucalbackend.Entity.Exemplo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemploRepository extends JpaRepository<Exemplo, Long> {

}