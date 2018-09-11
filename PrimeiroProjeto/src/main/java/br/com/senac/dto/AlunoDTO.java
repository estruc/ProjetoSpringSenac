package br.com.senac.dto;

import java.io.Serializable;

import br.com.senac.dominio.Aluno;

public class AlunoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Integer id;
	private String nome;
	private String email;


	public AlunoDTO() {

	}

	
	public AlunoDTO(Aluno aluno) {

		id = aluno.getId();

		nome = aluno.getNome();
		
		email = aluno.getEmail();

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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
