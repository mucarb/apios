package com.murilo.ordemservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilo.ordemservico.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
