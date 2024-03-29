package com.murilo.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.ordemservico.domain.Pessoa;
import com.murilo.ordemservico.domain.Tecnico;
import com.murilo.ordemservico.dtos.TecnicoDTO;
import com.murilo.ordemservico.repositories.PessoaRepository;
import com.murilo.ordemservico.repositories.TecnicoRepository;
import com.murilo.ordemservico.services.exceptions.DataIntegrityViolationException;
import com.murilo.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * Busca por id do Tecnico
	 */
	public Tecnico findById(Integer id) {
		/*
		 * Estanciando um para um tipo Optional<Tecnico> pois o objeto pode ou existir.
		 * E retornando uma excecao personalida caso nao encontro o objeto com o ID
		 * informado
		 */
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Técnico: objeto não encontrado! ID: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	/*
	 * Busca por todos os Tecnico na base de dados
	 */
	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	/*
	 * Inclui um novo tecnico na base dados
	 */
	public Tecnico create(TecnicoDTO objDto) {
		if (findByCPF(objDto) != null) {
			throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
		}
		Tecnico newObj = new Tecnico(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone());
		return tecnicoRepository.save(newObj);
	}

	/*
	 * Atualiza um tecnico na base dados
	 */
	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		/*
		 * Verificando se existe o id do tecnico
		 */
		Tecnico oldObj = findById(id);
		/*
		 * Verificando se ja existe outro Tecnico com o CPF a ser atualizado
		 */
		if (findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());

		return tecnicoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possuí Ordem de Serviço, não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}

	/* metodo para validacao do negocio, busca por Tecnico pelo CPF */
	private Pessoa findByCPF(TecnicoDTO objDto) {
		/* metodo findByCPF nao existe no repository */
		Pessoa obj = pessoaRepository.findByCPF(objDto.getCpf());

		if (obj != null) {
			return obj;
		}
		return null;
	}

}
