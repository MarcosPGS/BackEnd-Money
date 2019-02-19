package com.marcos.money.repository;

import com.marcos.money.entity.Categoria;

public interface CategoriaRepositoryQuery {
	
	public Categoria buscarPorNome(String nome);
}
