package com.marcos.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.money.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{



}
