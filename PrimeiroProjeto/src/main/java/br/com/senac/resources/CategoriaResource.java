package br.com.senac.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;


import org.springframework.web.bind.annotation.RequestBody;

import br.com.senac.dominio.Categoria;
import br.com.senac.dto.CategoriaDTO;
import br.com.senac.servico.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	/*
	@RequestMapping(method=RequestMethod.GET)
	public String testar() {
	 return "ESTÁ FUNCIONANDO!";
	}
	
	*/	
		
	//indica que agora a chamada da url vai incluir um id ex: categorias/1 ou categorias/2
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // PathVariable indica que o que foi mapeado no value do RequestMapping vai ser passado para o metodo
		Categoria objCategoria = service.buscar(id);
		return ResponseEntity.ok().body(objCategoria);
	   } // ResponseEntity encapsula varios tratamentos http para o rest e incluir o objeto categoria que encontramos
	
	
	@RequestMapping(method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('MONITOR')")
	public ResponseEntity<Void> inserir(@RequestBody Categoria objCategoria) { // @RequestBody faz objetos json ser convertidos em objetos em java

		objCategoria = service.inserir(objCategoria);

		// vamos montar a url resposta da categoria inserida
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objCategoria.getId()).toUri();

		// codigo http de criacao de objeto
		return ResponseEntity.created(uri).build();

	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('MONITOR')")
	public ResponseEntity<Void> alterar(@RequestBody Categoria objCategoria, @PathVariable Integer id) { // PathVariable indica que o que foi mapeado no value do RequestMapping vai ser passado para o metodo

		objCategoria.setId(id);

		objCategoria = service.alterar(objCategoria);

		// nao queremos que retorne nada
		return ResponseEntity.noContent().build();

	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('MONITOR')")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) { // PathVariable indica que o que foi mapeado no value do RequestMapping vai ser passado para o metodo
		
		service.excluir(id);
		
		// nao queremos que retorne nada
		return ResponseEntity.noContent().build();

	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> listarCategorias() {

		List<Categoria> listaCategorias = service.listaCategorias();

		// recurso do java 8 para montar uma lista conforme vc quiser
		List<CategoriaDTO> listDto = listaCategorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());  

		return ResponseEntity.ok().body(listDto);

	}
	
	// nao vamos fazer os valores por value
	// ex: na chamada do postman categorias/page/0/10/nome/desc
	// iremos fazer por parametro
	// ex: na chamada do postman categorias/paginacao?page=0&linesPerPage=10&orderBy=nome&direction=desc
	@RequestMapping(value="/paginacao", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, // defino valores padroes de se nao informar
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,  // defino valores padroes de se nao informar
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, // defino valores padroes de se nao informar
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Categoria> list = service.listaCategoriaPaginacao(page, linesPerPage, orderBy, direction);

		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));  

		return ResponseEntity.ok().body(listDto);

	}
	
	
	
}

