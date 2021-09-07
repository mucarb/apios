package com.murilo.ordemservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilo.ordemservico.domain.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {

}
