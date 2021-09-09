package com.murilo.ordemservico.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.ordemservico.domain.Cliente;
import com.murilo.ordemservico.domain.OrdemServico;
import com.murilo.ordemservico.domain.Tecnico;
import com.murilo.ordemservico.domain.enuns.Prioridade;
import com.murilo.ordemservico.domain.enuns.Status;
import com.murilo.ordemservico.dtos.OrdemServicoDTO;
import com.murilo.ordemservico.repositories.OrdemServicoRepository;
import com.murilo.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public OrdemServico findById(Integer id) {
		Optional<OrdemServico> obj = ordemServicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de Serviço não encontrada! ID: " + id + ", Tipo: " + OrdemServico.class.getName()));
	}

	public List<OrdemServico> findAll() {
		return ordemServicoRepository.findAll();
	}

	public OrdemServico create(@Valid OrdemServicoDTO objDto) {
		return fromDto(objDto);
	}

	public OrdemServico update(@Valid OrdemServicoDTO objDto) {
		findById(objDto.getId());
		return fromDto(objDto);
	}
	
	private OrdemServico fromDto(OrdemServicoDTO objDto) {
		OrdemServico newObj = new OrdemServico();

		newObj.setId(objDto.getId());
		newObj.setObservacoes(objDto.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(objDto.getPrioridade()));
		newObj.setStatus(Status.toEnum(objDto.getStatus()));

		Tecnico tec = tecnicoService.findById(objDto.getTecnico());
		Cliente cli = clienteService.findById(objDto.getCliente());

		newObj.setTecnico(tec);
		newObj.setCliente(cli);

		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}

		return ordemServicoRepository.save(newObj);
	}

}
