package br.com.senac.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senac.dominio.Aluno;
import br.com.senac.dto.AlunoCompletoDTO;
import br.com.senac.dto.AlunoDTO;
import br.com.senac.servico.AlunoService;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {

	@Autowired
	private AlunoService service;
	
		
		
	//indica que agora a chamada da url vai incluir um id ex: alunos/1 ou alunos/2
	// vou retorno um Aluno e não AlunoDTO pois quero ver tudo que esta relacionado ao aluno
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Aluno> buscar(@PathVariable Integer id) { // PathVariable indica que o que foi mapeado no value do RequestMapping vai ser passado para o metodo
		Aluno objAluno = service.buscar(id);
		return ResponseEntity.ok().body(objAluno);
	} // ResponseEntity encapsula varios tratamentos http para o rest e incluir o objeto Aluno que encontramos
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('MONITOR')")
	public ResponseEntity<List<AlunoDTO>> listarAlunos() {

		List<Aluno> listaAlunos = service.listaAlunos();

		// recurso do java 8 para montar uma lista conforme vc quiser
		List<AlunoDTO> listDto = listaAlunos.stream().map(aluno -> new AlunoDTO(aluno)).collect(Collectors.toList());  

		return ResponseEntity.ok().body(listDto);

	}
	
	// nao vamos fazer os valores por value
	// ex: na chamada do postman alunos/page/0/10/nome/desc
	// iremos fazer por parametro
	// ex: na chamada do postman alunos/paginacao?page=0&linesPerPage=10&orderBy=nome&direction=desc
	@RequestMapping(value="/paginacao", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('MONITOR')")
	public ResponseEntity<Page<AlunoDTO>> listaAlunosPorPagina(
			@RequestParam(value="page", defaultValue="0") Integer page, // defino valores padroes de se nao informar
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,  // defino valores padroes de se nao informar
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, // defino valores padroes de se nao informar
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Aluno> list = service.listaAlunoPaginacao(page, linesPerPage, orderBy, direction);

		Page<AlunoDTO> listDto = list.map(obj -> new AlunoDTO(obj));  

		return ResponseEntity.ok().body(listDto);

	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> inserir(@RequestBody AlunoCompletoDTO objDto) {

		Aluno obj = service.criarAlunoCompleto(objDto);

		obj = service.inserir(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Aluno objAluno, @PathVariable Integer id) { // PathVariable indica que o que foi mapeado no value do RequestMapping vai ser passado para o metodo

		objAluno.setId(id);

		objAluno = service.alterar(objAluno);

		// nao queremos que retorne nada
		return ResponseEntity.noContent().build();

	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('MONITOR')")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {

		service.delete(id);

		return ResponseEntity.noContent().build();

	}
	
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}

	
	
	
}

