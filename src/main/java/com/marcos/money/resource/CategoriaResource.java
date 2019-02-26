package com.marcos.money.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.money.entity.Categoria;
import com.marcos.money.negocios.service.CategoriaService;
import exceptions.CategoriaDuplicadoException;

@CrossOrigin
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
//	LISTA TODOS
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Categoria> categorias = categoriaService.listarTodos();		
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
	}
	// BUSCAR POR NOME
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Object> buscarPorNome(@PathVariable String nome){
		Categoria categoriaEncontrada = categoriaService.buscarPorNome(nome);
		if(categoriaEncontrada == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria Não Encontrada!");
		}
		
		return ResponseEntity.ok(categoriaEncontrada);
	}
	
	// BUSCAR POR ID
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarID(@PathVariable Long codigo){
		Categoria codigoEncontrado = categoriaService.buscarID(codigo);
		if (codigoEncontrado !=null)
			return ResponseEntity.ok(codigoEncontrado);
		else
			return ResponseEntity.notFound().build();
	}

//	CRIANDO  ENTIDADE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> salvar(@Valid @RequestBody Categoria categoria){
		try {
			return ResponseEntity.ok().body(categoriaService.salvar(categoria));
		} catch (CategoriaDuplicadoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	//	DELETANDO ENTIDADE
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		categoriaService.delelar(codigo);
	}
	
	@PutMapping
	public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.ok(categoriaService.atualizar(categoria));
	}
	

}
