package com.marcos.money.negocios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.money.entity.Lancamento;
import com.marcos.money.entity.Pessoa;
import com.marcos.money.repository.LancamentoRepository;
import com.marcos.money.repository.PessoaRepository;
import com.marcos.money.repository.filter.LancamentoFilter;

import exceptions.LancamentoDuplicadoException;
import exceptions.LancamentoNotFundException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
//	LISTAR TODOS
	public List<Lancamento> pesquisaFiltrada(LancamentoFilter lancamentoFilter){
		return lancamentoRepository.filtar(lancamentoFilter);	}
	
//	BUSCAR POR ID
	public Lancamento buscarPorID( Long codigo) throws LancamentoNotFundException {
		Lancamento lancamentoEncontrado = lancamentoRepository.buscarPorID(codigo);
		if(lancamentoEncontrado == null) {
			 throw new LancamentoNotFundException("Lancamento não Encontrado!");
		}
		return lancamentoEncontrado;
	}	
//	SALVAR COMPLETO
	
	public Lancamento criar(Lancamento lancamento) throws LancamentoDuplicadoException, exceptions.PessoaInexistenteException {
	if(lancamento != null && lancamento.getCodigo() != null) {
				
		Lancamento lancamentoEncontrado = lancamentoRepository.buscarPorID(lancamento.getCodigo());
				
				if(lancamentoEncontrado != null) {
					throw new LancamentoDuplicadoException("Lancamento Duplicado!" + " ID: " + lancamentoEncontrado.getCodigo() +
							" Nome: " + lancamentoEncontrado.getDescricao());
				}
				
			}
			Pessoa pessoa = pessoaRepository.buscarPorID(lancamento.getPessoa().getCodigo());
			if(pessoa == null || !pessoa.isAtivo()) {
				throw new exceptions.PessoaInexistenteException("Pessoa Inesxistente - Inativa");
			}
		
	
			
			return lancamentoRepository.save(lancamento);
		}
//	DELETAR
	public void deletar(Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}
}
