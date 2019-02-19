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

import com.marcos.money.entity.Categoria;
import com.marcos.money.repository.CategoriaRepositoryQuery;


@Repository
public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	@Override
	public Categoria buscarPorNome(String nome) {
		Categoria categoriaEncontrada = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Categoria> categoriaCR = builder.createQuery(Categoria.class);
			Root<Categoria> categoriaRoot = categoriaCR.from(Categoria.class);
			Predicate[] predicates = criarRestricao(nome,builder, categoriaRoot);
			categoriaCR.where(predicates);
			
			TypedQuery<Categoria> typedQuery = manager.createQuery(categoriaCR);
			categoriaEncontrada = typedQuery.getSingleResult();
			
			return categoriaEncontrada;
			
		} catch (Exception e) {
			return categoriaEncontrada;
		}
	}
	private Predicate[] criarRestricao(String nome, CriteriaBuilder builder, Root<Categoria> categoriaRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(nome)) {
			predicates.add(builder.like(builder.lower(categoriaRoot.get("nome")), "%" + (nome.toLowerCase()) + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
