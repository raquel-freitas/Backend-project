package com.example.organiza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.organiza.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
   
}
