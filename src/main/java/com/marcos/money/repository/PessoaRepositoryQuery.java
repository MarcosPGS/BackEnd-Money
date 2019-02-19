package com.marcos.money.repository;

import com.marcos.money.entity.Pessoa;

public interface PessoaRepositoryQuery {
	
 public Pessoa buscarPorNome(String nome);
 public Pessoa buscarPorID(Long codigo);
}
