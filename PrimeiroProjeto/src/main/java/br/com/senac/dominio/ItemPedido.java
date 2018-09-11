package br.com.senac.dominio;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double desconto;
	private Integer quantidade;
	private Double valor;

	@JsonIgnore // o ItemPedido nao precisa saber que é o pedido para o json. Isso ja vai
				// acontecer na classe Pedido
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	public ItemPedido() {

	}

	public ItemPedido(Pedido pedido, Curso curso, Double desconto, Integer quantidade, Double valor) {
		super();
		id.setPedido(pedido);
		id.setCurso(curso);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.valor = valor;

	}

	public double getSubTotal() {
		return (valor - desconto) * quantidade;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	// isso aqui ajuda a ter acesso ao pedido e ao produto sem ter que acessar a
	// classe ItenPedidoPK
	@JsonIgnore // nao preciso saber em itemPedido, mas preciso saber em pedido
	public Pedido getPedido() {
		return id.getPedido();
	}

	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	public Curso getCurso() {
		return id.getCurso();
	}
	
	
	public void setCurso(Curso curso) {

		id.setCurso(curso);

	}
}
