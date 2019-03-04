package com.marcos.money.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marcos.money.entity.Lancamento;
import com.marcos.money.repository.filter.LancamentoFilter;
import com.marcos.money.repository.projetion.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	public Lancamento buscaPorDescricao(String descricao );
	public Lancamento buscarPorID(Long codigo);
	public Page<Lancamento>filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento>resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	
	
}
