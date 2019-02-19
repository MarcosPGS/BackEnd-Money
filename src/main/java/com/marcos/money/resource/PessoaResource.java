package com.marcos.money.resource;

import java.util.List;
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

import com.marcos.money.entity.Pessoa;
import com.marcos.money.negocios.service.PessoaService;

import exceptions.PessoaDuplicadaException;
import exceptions.PessoaNotFoundDeleteExcption;
import exceptions.PessoaNotFundException;

@CrossOrigin
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
//	LISTA TODOS
	@GetMapping
	public ResponseEntity<?> listaTodos(){
		List<Pessoa> pessoas = pessoaService.listar();
		return !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build();
	}
	
	// BUSCAR POR ID
	@GetMapping("/{codigo}")
	public ResponseEntity<Object> buscarPorID(@PathVariable Long codigo){
	try {
		return ResponseEntity.ok(pessoaService.buscarID(codigo));
	} catch (PessoaNotFoundDeleteExcption e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	}
	
	// BUSCAR POR NOME
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Object> buscarPorNome(@PathVariable String nome){
		Pessoa pessoaEncontrada = pessoaService.buscarPorNome(nome);
		if(pessoaEncontrada == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa Não Encontrada!");
		}
		
		return ResponseEntity.ok(pessoaEncontrada);
	}
	
//	CRIANDO  ENTIDADE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> salvar(@Valid @RequestBody Pessoa pessoa){
		try {
			return ResponseEntity.ok().body(pessoaService.criar(pessoa));
		} catch (PessoaDuplicadaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
//	DELETANDO ENTIDADE
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		pessoaService.deletar(codigo);
	}
	
//	ATUALIZAR ENTIDADE
	@PutMapping
	public ResponseEntity<Pessoa> atualizar(@Valid @RequestBody Pessoa pessoa){
		return ResponseEntity.ok(pessoaService.atualizar(pessoa));
	}
	
//	ATUALIZAR ENTIDADE ATIVA
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		try {
			pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
		} catch (PessoaNotFundException e) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	// BUSCAR POR ID
	@GetMapping("/id/{codigo}")
	public ResponseEntity<Object> buscarPorCodigo(@PathVariable Long codigo){
		
		try {
			return ResponseEntity.ok(pessoaService.buscarPorId(codigo));
		} catch (PessoaNotFoundDeleteExcption e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
}
