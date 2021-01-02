package com.sicredi.assembleia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.assembleia.model.Pauta;

@Repository
public interface PautaRepository extends CrudRepository<Pauta, Long> {

}
