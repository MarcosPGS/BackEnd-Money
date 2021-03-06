package com.marcos.money.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.money.entity.Lancamento;
import com.marcos.money.negocios.service.LancamentoService;
import com.marcos.money.repository.filter.LancamentoFilter;
import com.marcos.money.repository.projetion.ResumoLancamento;

import exceptions.LancamentoDuplicadoException;
import exceptions.LancamentoNotFundException;
import exceptions.PessoaInexistenteException;

@CrossOrigin
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService  lancamentoService;
	

	
//	LISTAR TODOS
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> pesquisar( LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoService.pesquisaFiltrada(lancamentoFilter, pageable);
	}
	
	
//	LISTAR TODOS
	@GetMapping(params="resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> resumir( LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoService.resumir(lancamentoFilter, pageable);
	}
	
	
	
//	public ResponseEntity<?> pesquisar(LancamentoFilter lancamentoFilter){
//		List<Lancamento> lancamentos = lancamentoService.listar();
//		return !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
//		}
	
//	BUSCAR POR ID
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long codigo) {
		try {
			return ResponseEntity.ok(lancamentoService.buscarPorID(codigo));
		} catch (LancamentoNotFundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
//	CRIAR
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> criar(@Valid @RequestBody Lancamento lancamento) {
		
			try {
				return ResponseEntity.ok(lancamentoService.criar(lancamento));
			} catch (LancamentoDuplicadoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch (PessoaInexistenteException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	
}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void deletar(@PathVariable Long codigo) {
		lancamentoService.deletar(codigo);
	}
}
