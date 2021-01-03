package com.sicredi.assembleia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sicredi.assembleia.dto.CompiladoVotos;
import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Voto;
import com.sicredi.assembleia.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private AssociadoService associadoService;

	public Voto votar(Long pautaId, Long associadoId, boolean voto) {
		validarVoto(pautaId, associadoId);
		return votoRepository.save(Voto.builder().associadoId(associadoId).pautaId(pautaId).aprova(voto).build());
	}

	protected void validarVoto(Long pautaId, Long associadoId) {
		pautaService.validarPauta(pautaId);
		associadoService.validarAssociado(associadoId);
		validarVotoJaComputado(pautaId, associadoId);
	}

	protected void validarVotoJaComputado(Long pautaId, Long associadoId) {
		if (votoRepository.existsByPautaIdAndAssociadoId(pautaId, associadoId)) {
			throw new BusinessException(HttpStatus.NOT_ACCEPTABLE, "Associado já votou na pauta.");
		}
	}

	public CompiladoVotos consultarVotosCompilados(Long pautaId) {
		return votoRepository.consultarVotosCompilados(pautaId);
	}

}
