package com.br.ages.orientacaobucalbackend.Services;

import com.br.ages.orientacaobucalbackend.DataAcess.Repository.ExemploRepository;
import com.br.ages.orientacaobucalbackend.Entity.Exemplo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemploService {

    @Autowired
    private ExemploRepository exemploRepository;

    public List<Exemplo> list(){
        return exemploRepository.findAll();
    }

    public void deleteAll(){
        exemploRepository.deleteAll();
    }

    public void save(String name) {
        exemploRepository.save(new Exemplo(name));
    }
}
