package br.com.senac.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Categoria;
import br.com.senac.dominio.Curso;
import br.com.senac.repositorio.CategoriaRepositorio;
import br.com.senac.repositorio.CursoRepositorio;
import br.com.senac.servico.exception.ObjectNotFoundException;


@Service
public class CursoService {

	
	@Autowired
	private CursoRepositorio repoCurso;
	
	@Autowired
	private CategoriaRepositorio repoCategoria;

	
	// Integer page - pagina atual
	//Integer linesPerPage - resultados por pagina
	// String orderBy - ordenador por qual coluna
	//String direction - sera ordernado por desc ou asc
	public Page<Curso> listaCursoPorCategorias(String nome, List<Integer> idsCategorias,Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repoCategoria.findAllById(idsCategorias);
		return repoCurso.buscarCursoPorCategoria(nome, categorias, pageRequest); 
	}
	
	public Curso buscar(Integer id) {

		Optional<Curso> obj = repoCurso.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Curso.class.getName()));

	}
	
	public List<Curso> buscarPorNome(String nome) {

		List<Curso> lista = repoCurso.findByNome(nome);

		return lista;

	}
	
}
