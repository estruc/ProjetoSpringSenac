package br.com.senac.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>  {

	@Transactional(readOnly=true)
	Page<Pedido> findByAluno(Aluno aluno, Pageable pageRequest);
	
}
