package com.sicredi.assembleia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.assembleia.model.Associado;

@Repository
public interface AssociadoRepository extends CrudRepository<Associado, Long> {
	
	public boolean existsByCpf(String cpf);
	
}
