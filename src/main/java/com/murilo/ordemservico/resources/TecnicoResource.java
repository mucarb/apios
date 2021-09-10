package com.murilo.ordemservico.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.murilo.ordemservico.domain.Tecnico;
import com.murilo.ordemservico.dtos.TecnicoDTO;
import com.murilo.ordemservico.services.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	/*
	 * Especificando um endpoint. Ex.: localhost:8080/tecnicos
	 */
	
	@Autowired
	private TecnicoService tecnicoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		Tecnico obj = tecnicoService.findById(id);
		TecnicoDTO objDto = new TecnicoDTO(obj);
		return ResponseEntity.ok().body(objDto);
	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		/* conversao N formas da lista de Tecnico para TecnicoDTO */
		
		List<TecnicoDTO> listDto = tecnicoService.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());

//		List<Tecnico> list = tecnicoService.findAll();
//		List<TecnicoDTO> listDto = new ArrayList<>();

//		for (Tecnico obj : list) {
//			listDto.add(new TecnicoDTO(obj));
//		}

//		list.forEach(obj -> listDto.add(new TecnicoDTO(obj)));

		return ResponseEntity.ok().body(listDto);
	}

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDto) {
		Tecnico newObj = tecnicoService.create(objDto);
		
		/* passando a uri de acesso do novo obj */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDto) {
		TecnicoDTO newObj = new TecnicoDTO(tecnicoService.update(id, objDto));
		return ResponseEntity.ok().body(newObj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		tecnicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
