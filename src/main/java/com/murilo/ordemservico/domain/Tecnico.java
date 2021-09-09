package com.murilo.ordemservico.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tecnico extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * Anotacao declarando que esse atributo faz juncao de Um para Muitos mapeada
	 * por outro atributo de outra classe com nome de tecnico
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "tecnico")
	private List<OrdemServico> list = new ArrayList<>();

	public Tecnico() {
	}

	public Tecnico(Integer id, String nome, String cpf, String telefone) {
		super(id, nome, cpf, telefone);
	}

	public List<OrdemServico> getList() {
		return list;
	}

	public void setList(List<OrdemServico> list) {
		this.list = list;
	}

}
