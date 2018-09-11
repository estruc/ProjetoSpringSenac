package br.com.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.dominio.Pagamento;

@Repository
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Integer>  {

}
