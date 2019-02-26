package com.marcos.money.negocios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.marcos.money.entity.Categoria;
import com.marcos.money.repository.CategoriaRepository;


import exceptions.CategoriaDuplicadoException;

@Service
public class CategoriaService {
	@Autowired
	CategoriaRepository categoriaRepository;
	
//	Listar
	public List<Categoria>listarTodos(){
		return categoriaRepository.findAll();
	}
// Salvar
	public Categoria salvar(Categoria categoria) throws CategoriaDuplicadoException {
		Categoria categoriaEncontrado = categoriaRepository.buscarPorNome(categoria.getNome());
		if(categoriaEncontrado != null) {
			throw new CategoriaDuplicadoException("Categoria Duplicado - " + "ID: " + categoriaEncontrado.getCodigo());
		}
		
		
		return categoriaRepository.save(categoria);
	}
	
//	Buscar por iD
	
	public Categoria buscarID(Long codigo){
		return categoriaRepository.findOne(codigo);
	}
	
//	deletar
	public void delelar(Long codigo) {
		categoriaRepository.delete(codigo);
	}
	
//	buscar por nome unico
	public Categoria buscarPorNome(String nome) {
		return categoriaRepository.buscarPorNome(nome);
	}
	
//	atualizar
	
	public Categoria atualizar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	
}
