package br.com.senac.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senac.dominio.Curso;
import br.com.senac.dto.CursoDTO;
import br.com.senac.servico.CursoService;
import br.com.senac.utils.URL;

@RestController
@RequestMapping("/cursos")
public class CursoResource {

	@Autowired
	private CursoService service;
	
	
	
	
	// a chamada sera assim: cursos/nome=Java&categorias=1,3,5
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CursoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Curso> list = service.listaCursoPorCategorias(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<CursoDTO> listDto = list.map(obj -> new CursoDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
	
	
	
}

