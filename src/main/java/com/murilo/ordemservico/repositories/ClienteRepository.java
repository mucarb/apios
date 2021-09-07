package com.murilo.ordemservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilo.ordemservico.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
