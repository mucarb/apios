package com.murilo.ordemservico.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.murilo.ordemservico.domain.Cliente;

/* Classes Padrão DTO (Data Transfer Object). Utilizado para omitir dados das classes de dominio otimizando a informacao */
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Campo NOME deve ser preenchido!")
	private String nome;
	
	@CPF
	@NotEmpty(message = "Campo CPF deve ser preenchido!")
	private String cpf;
	
	@NotEmpty(message = "Campo TELEFONE deve ser preenchido!")
	private String telefone;
	
	public ClienteDTO() {
	}
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.telefone = obj.getTelefone();
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
