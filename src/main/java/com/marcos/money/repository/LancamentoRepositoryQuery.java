package com.marcos.money.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcos.money.entity.Lancamento;
import com.marcos.money.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Lancamento buscaPorDescricao(String descricao );
	public Lancamento buscarPorID(Long codigo);
	public Page<Lancamento>filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	
}
