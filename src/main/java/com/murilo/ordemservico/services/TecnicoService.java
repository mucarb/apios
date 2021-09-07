package com.murilo.ordemservico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.ordemservico.domain.Tecnico;
import com.murilo.ordemservico.dtos.TecnicoDTO;
import com.murilo.ordemservico.repositories.TecnicoRepository;
import com.murilo.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Técnico: objeto não encontrado! ID: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO objDto) {
		Tecnico newObj = new Tecnico(null, objDto.getNome(), objDto.getCpf(), objDto.getTelefone());
		return tecnicoRepository.save(newObj);
	}

}
