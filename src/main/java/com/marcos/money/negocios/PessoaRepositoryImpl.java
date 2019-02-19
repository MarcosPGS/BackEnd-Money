package com.marcos.money.negocios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import com.marcos.money.entity.Pessoa;
import com.marcos.money.repository.PessoaRepositoryQuery;

@Repository
public class PessoaRepositoryImpl  implements PessoaRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	@Override
	public Pessoa buscarPorNome(String nome) {
		Pessoa pessoaEncontrada = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Pessoa> pessoaCR = builder.createQuery(Pessoa.class);
			Root<Pessoa> pessoaRoot = pessoaCR.from(Pessoa.class);
			Predicate[] predicates = criarRestricao(nome,builder, pessoaRoot);
			pessoaCR.where(predicates);
			
			TypedQuery<Pessoa> typedQuery = manager.createQuery(pessoaCR);
			pessoaEncontrada = typedQuery.getSingleResult();
			
			return pessoaEncontrada;
			
		} catch (Exception e) {
			return pessoaEncontrada;
		}
	}
	private Predicate[] criarRestricao(String nome, CriteriaBuilder builder, Root<Pessoa> pessoaRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(nome)) {
			predicates.add(builder.like(builder.lower(pessoaRoot.get("nome")), "%" + (nome.toLowerCase()) + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	@Override
	public Pessoa buscarPorID(Long codigo) {
		Pessoa pessoaEncontrada = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Pessoa> pessoaCR = builder.createQuery(Pessoa.class);
			Root<Pessoa> pessoaRoot = pessoaCR.from(Pessoa.class);
			Predicate[] predicates = criarRestricao(codigo,builder, pessoaRoot);
			pessoaCR.where(predicates);
			
			TypedQuery<Pessoa> typedQuery = manager.createQuery(pessoaCR);
			pessoaEncontrada = typedQuery.getSingleResult();
			
			return pessoaEncontrada;
			
		} catch (Exception e) {
			return pessoaEncontrada;
		}
	}
	private Predicate[] criarRestricao(Long codigo, CriteriaBuilder builder, Root<Pessoa> pessoaRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if(codigo.equals(codigo)) {
			predicates.add(builder.equal(pessoaRoot.get("codigo"),codigo ));
			
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	

}



	
