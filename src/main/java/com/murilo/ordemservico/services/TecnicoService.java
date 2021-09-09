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

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Técnico: objeto não encontrado! ID: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO objDto) {
		if (findByCPF(objDto) != null) {
			throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
		}
		Tecnico newObj = new Tecnico(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone());
		return tecnicoRepository.save(newObj);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		Tecnico oldObj = findById(id);

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

	private Pessoa findByCPF(TecnicoDTO objDto) {
		Pessoa obj = pessoaRepository.findByCPF(objDto.getCpf());

		if (obj != null) {
			return obj;
		}
		return null;
	}

}
