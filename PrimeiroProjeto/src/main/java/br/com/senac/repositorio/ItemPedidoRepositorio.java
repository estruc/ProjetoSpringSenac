package br.com.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.dominio.ItemPedido;

@Repository
public interface ItemPedidoRepositorio extends JpaRepository< ItemPedido, Integer> {

	
}
