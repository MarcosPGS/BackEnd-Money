package com.marcos.money.negocios.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.money.entity.Pessoa;
import com.marcos.money.repository.PessoaRepository;

import exceptions.PessoaDuplicadaException;
import exceptions.PessoaNotFoundDeleteExcption;
import exceptions.PessoaNotFundException;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pessoaRepository;
//	lista todos
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
//Buscar Por Nome
	public Pessoa buscarPorNome(String nome) {
		return pessoaRepository.buscarPorNome(nome);
	}
	
//buscar por ID
	public Pessoa buscarID(Long codigo) throws PessoaNotFoundDeleteExcption{
		Pessoa pessoaSalva = pessoaRepository.buscarPorID(codigo);
		if(pessoaSalva == null) {
			throw new PessoaNotFoundDeleteExcption("Pessoa não Encontrada!");
		}
		return pessoaSalva;
	}
	
//	criar 
	public Pessoa criar( Pessoa pessoa) throws PessoaDuplicadaException {
		Pessoa pessoaEncontra = pessoaRepository.buscarPorNome(pessoa.getNome());
		if(pessoaEncontra != null) {
			throw new PessoaDuplicadaException("Pessoa Duplicada! ID: "+ pessoaEncontra.getCodigo());		}
		return pessoaRepository.save(pessoa);
	}
	
//	deletar
	
	public void deletar(Long codigo) {
		pessoaRepository.deleteById(codigo);
		
	}
//	atualizar
	
	public Pessoa atualizar( Pessoa pessoa) {
		
		return pessoaRepository.save(pessoa);
	}
	
//	atualizar por ID
	
	public void atualizarPropriedadeAtivo( Long codigo, Boolean ativo) throws PessoaNotFundException {
		Pessoa pessoaEncontrada = buscarPessoaPeloCodigo(codigo);
		pessoaEncontrada.setAtivo(ativo);
		pessoaRepository.save(pessoaEncontrada);
	}
	
	private Pessoa buscarPessoaPeloCodigo(long codigo) throws PessoaNotFundException  {
		Pessoa pessoaSalva = pessoaRepository.buscarPorID(codigo);
		if(pessoaSalva == null) {
			throw new PessoaNotFundException("Pessoa não Encontrada!");
		}
		return pessoaSalva;
	}
	
//	atualizar por ID
	public Pessoa buscarPorId(long codigo) throws PessoaNotFoundDeleteExcption {
		Pessoa pessoaSalva = pessoaRepository.buscarPorID(codigo);
		if(pessoaSalva == null) {
			throw new PessoaNotFoundDeleteExcption("Pessoa não Encontrada!");
		}
		return pessoaSalva;

}
}
