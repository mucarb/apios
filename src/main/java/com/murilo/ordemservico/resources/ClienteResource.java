package com.murilo.ordemservico.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.murilo.ordemservico.domain.Cliente;
import com.murilo.ordemservico.dtos.ClienteDTO;
import com.murilo.ordemservico.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	/*
	 * Especificando um endpoint. Ex.: localhost:8080/clientes
	 */
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente obj = clienteService.findById(id);
		ClienteDTO objDto = new ClienteDTO(obj);
		return ResponseEntity.ok().body(objDto);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		/* conversao N formas da lista de Cliente para ClienteDTO */

		List<ClienteDTO> listDto = clienteService.findAll().stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());

//		List<Cliente> list = clienteService.findAll();
//		List<ClienteDTO> listDto = new ArrayList<>();

//		for (Cliente obj : list) {
//			listDto.add(new ClienteDTO(obj));
//		}

//		list.forEach(obj -> listDto.add(new ClienteDTO(obj)));

		return ResponseEntity.ok().body(listDto);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDto) {
		Cliente newObj = clienteService.create(objDto);
		
		/* passando a uri de acesso do novo obj */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto) {
		ClienteDTO newObj = new ClienteDTO(clienteService.update(id, objDto));
		return ResponseEntity.ok().body(newObj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
