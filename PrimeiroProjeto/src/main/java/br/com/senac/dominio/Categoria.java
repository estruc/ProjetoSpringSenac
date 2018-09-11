package br.com.senac.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	// se estou utilizando @JsonIgnore  entao nao preciso indicar o @JsonManagedReference 
	//@JsonManagedReference // indica quem eu quero que venha no meu json
	@ManyToMany(mappedBy="categorias")
	private List<Curso> cursos = new ArrayList<>();
	
	
	public Categoria() {
		
	}

	
	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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


	public List<Curso> getCursos() {
		return cursos;
	}


	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
	
	
	
	
}