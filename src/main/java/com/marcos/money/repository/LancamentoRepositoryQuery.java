package com.marcos.money.repository;

import java.util.List;

import com.marcos.money.entity.Lancamento;
import com.marcos.money.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public Lancamento buscaPorDescricao(String descricao );
	public Lancamento buscarPorID(Long codigo);
	public List<Lancamento>filtar(LancamentoFilter lancamentoFilter);
}
