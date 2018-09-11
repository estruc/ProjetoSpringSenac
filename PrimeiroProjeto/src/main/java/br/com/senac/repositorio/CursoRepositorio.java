package br.com.senac.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Categoria;
import br.com.senac.dominio.Curso;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, Integer> {

	// vamos buscar os cursos com like informando por quais categorias eu quero buscar
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Curso obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Curso> buscarCursoPorCategoria(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	
	// outta forma de fazer é usar os padroes do spring data pelo nome do metodo ele já sabe o que tem que fazer.
	// EXEMPLO ESTA NO SITE https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
	//Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	
	@Query("select a from Curso a where a.nome = ?1")
	List<Curso> findByNome(String nome);
}
