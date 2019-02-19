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

import com.marcos.money.entity.Lancamento;
import com.marcos.money.entity.Lancamento_;
import com.marcos.money.repository.LancamentoRepositoryQuery;
import com.marcos.money.repository.filter.LancamentoFilter;

@Repository
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Lancamento buscaPorDescricao(String descricao) {
		Lancamento lancamentoEncontrada = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Lancamento> lancamentoCR = builder.createQuery(Lancamento.class);
			Root<Lancamento> lancamentoRoot = lancamentoCR.from(Lancamento.class);
			Predicate[] predicates = criarRestricao(descricao, builder, lancamentoRoot);
			lancamentoCR.where(predicates);

			TypedQuery<Lancamento> typedQuery = manager.createQuery(lancamentoCR);
			lancamentoEncontrada = typedQuery.getSingleResult();

			return lancamentoEncontrada;

		} catch (Exception e) {
			return lancamentoEncontrada;
		}
	}

	private Predicate[] criarRestricao(String descricao, CriteriaBuilder builder, Root<Lancamento> lancamentoRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if (!StringUtils.isEmpty(descricao)) {
			predicates.add(builder.like(builder.lower(lancamentoRoot.get("descricao")),
					"%" + (descricao.toLowerCase()) + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Lancamento buscarPorID(Long codigo) {
		Lancamento lancamentoEncontrada = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Lancamento> lancamentoCR = builder.createQuery(Lancamento.class);
			Root<Lancamento> lancamentoRoot = lancamentoCR.from(Lancamento.class);
			Predicate[] predicates = criarRestricao(codigo, builder, lancamentoRoot);
			lancamentoCR.where(predicates);

			TypedQuery<Lancamento> typedQuery = manager.createQuery(lancamentoCR);
			lancamentoEncontrada = typedQuery.getSingleResult();

			return lancamentoEncontrada;

		} catch (Exception e) {
			return lancamentoEncontrada;
		}
	}

	private Predicate[] criarRestricao(Long codigo, CriteriaBuilder builder, Root<Lancamento> lancamentoRoot) {
		List<Predicate> predicates = new ArrayList<>();
		if (codigo.equals(codigo)) {
			predicates.add(builder.equal(lancamentoRoot.get("codigo"), codigo));

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public List<Lancamento> filtar(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

//		Criar as Restrições
		Predicate[] predicates = criarRestricao(lancamentoFilter, builder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Lancamento> typedQuery = manager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	private Predicate[] criarRestricao(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.DESCRICAO)), "%" + (lancamentoFilter.getDescricao().toLowerCase())+"%"));

		}

		if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())){
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoDe()));
		}

		if ((!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte()))) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
