package com.sicredi.assembleia.repository;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.assembleia.dto.CompiladoVotos;
import com.sicredi.assembleia.model.Voto;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long> {

	public boolean existsByPautaIdAndAssociadoId(Long pautaId, Long associadoId);

	@Query(value = "SELECT COUNT(*) AS totalVotos, "
			+ "(SELECT COUNT(*) FROM Voto WHERE pauta_id = :pautaId AND aprova IS TRUE) AS quantVotosFavoraveis, "
			+ "(SELECT COUNT(*) FROM Voto WHERE pauta_id = :pautaId AND aprova IS FALSE) AS quantVotosContra "
			+ "FROM Voto " + "WHERE pauta_id = :pautaId", nativeQuery = true)
	public CompiladoVotos consultarVotosCompilados(@PathParam("pautaId") Long pautaId);

}
