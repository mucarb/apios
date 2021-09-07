package com.murilo.ordemservico.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.ordemservico.domain.Cliente;
import com.murilo.ordemservico.domain.OrdemServico;
import com.murilo.ordemservico.domain.Tecnico;
import com.murilo.ordemservico.domain.enuns.Prioridade;
import com.murilo.ordemservico.domain.enuns.Status;
import com.murilo.ordemservico.repositories.ClienteRepository;
import com.murilo.ordemservico.repositories.OrdemServicoRepository;
import com.murilo.ordemservico.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Murilo Ribeiro", "460.061.168-39", "(18) 98819-4424");
		Tecnico t2 = new Tecnico(null, "Linus Torvalds", "641.760.040-88", "(18) 94545-4545");

		Cliente c1 = new Cliente(null, "Betina Campos", "438.562.870-00", "(18) 99116-9600");

		OrdemServico os1 = new OrdemServico(null, Prioridade.ALTA, "Trocar cartucho do Notebook", Status.ANDAMENTO, t1,
				c1);

		t1.getList().addAll(Arrays.asList(os1));
		c1.getList().addAll(Arrays.asList(os1));

		tecnicoRepository.saveAll(Arrays.asList(t1, t2));

		clienteRepository.saveAll(Arrays.asList(c1));

		ordemServicoRepository.saveAll(Arrays.asList(os1));
	}

}
