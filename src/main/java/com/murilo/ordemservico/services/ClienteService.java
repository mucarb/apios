package com.murilo.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.ordemservico.domain.Cliente;
import com.murilo.ordemservico.domain.Pessoa;
import com.murilo.ordemservico.dtos.ClienteDTO;
import com.murilo.ordemservico.repositories.ClienteRepository;
import com.murilo.ordemservico.repositories.PessoaRepository;
import com.murilo.ordemservico.services.exceptions.DataIntegrityViolationException;
import com.murilo.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * Busca por id do Cliente
	 */
	public Cliente findById(Integer id) {
		/*
		 * Estanciando um para um tipo Optional<Cliente> pois o objeto pode ou existir.
		 * E retornando uma excecao personalida caso nao encontro o objeto com o ID
		 * informado
		 */
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente: objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	/*
	 * Busca por todos os Cliente na base de dados
	 */
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	/*
	 * Inclui um novo cliente na base dados
	 */
	public Cliente create(ClienteDTO objDto) {
		if (findByCPF(objDto) != null) {
			throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
		}
		Cliente newObj = new Cliente(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone());
		return clienteRepository.save(newObj);
	}

	/*
	 * Atualiza um cliente na base dados
	 */
	public Cliente update(Integer id, @Valid ClienteDTO objDto) {
		/*
		 * Verificando se existe o id do cliente
		 */
		Cliente oldObj = findById(id);
		/*
		 * Verificando se ja existe outro Cliente com o CPF a ser atualizado
		 */
		if (findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
		}

		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());

		return clienteRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);

		if (obj.getList().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possuí Ordem de Serviço, não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}

	/* metodo para validacao do negocio, busca por Cliente pelo CPF */
	private Pessoa findByCPF(ClienteDTO objDto) {
		/* metodo findByCPF nao existe no repository */
		Pessoa obj = pessoaRepository.findByCPF(objDto.getCpf());

		if (obj != null) {
			return obj;
		}
		return null;
	}

}
