package br.com.senac.dto;

import java.io.Serializable;

import br.com.senac.dominio.Curso;

public class CursoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
    private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private Double preco;
	
	
	public CursoDTO() {
		
	}
	
	public CursoDTO(Curso obj) {
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	
	
	
	
	
}
