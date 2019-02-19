package com.marcos.money.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.money.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
