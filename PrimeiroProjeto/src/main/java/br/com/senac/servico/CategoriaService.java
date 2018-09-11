package br.com.senac.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Categoria;
import br.com.senac.repositorio.CategoriaRepositorio;
import br.com.senac.servico.exception.ObjectNotFoundException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;


@Service
public class CategoriaService {

	
	@Autowired
	private CategoriaRepositorio repoCat;

	
	public Categoria buscar(Integer id) {
		Optional<Categoria> objCategoria = repoCat.findById(id);
		return objCategoria.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encontrada! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria inserir(Categoria objCategoria) {
		// estou colocando um objeto novo entao o id precisa ser null
		objCategoria.setId(null);
		return repoCat.save(objCategoria);
	}
	
	public Categoria alterar(Categoria objCategoria) {

		Categoria objCategoriaEncontrado = buscar(objCategoria.getId());
		objCategoriaEncontrado.setNome(objCategoria.getNome());

		return repoCat.save(objCategoriaEncontrado);

	}
	
	
	public void excluir(Integer id) {

		repoCat.deleteById(id);

	}
	
	public List<Categoria> listaCategorias() {

		return repoCat.findAll();

	}
	
	// Integer page - pagina atual
	//Integer linesPerPage - resultados por pagina
	// String orderBy - ordenador por qual coluna
	//String direction - sera ordernado por desc ou asc
	public Page<Categoria> listaCategoriaPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repoCat.findAll(pageRequest);

	}
	
}
