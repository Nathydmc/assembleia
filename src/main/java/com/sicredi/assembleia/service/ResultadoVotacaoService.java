package com.sicredi.assembleia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.assembleia.dto.CompiladoVotos;
import com.sicredi.assembleia.dto.ResultadoVotacao;
import com.sicredi.assembleia.enumerator.StatusResultado;
import com.sicredi.assembleia.enumerator.StatusVotacao;
import com.sicredi.assembleia.model.Pauta;

@Service
public class ResultadoVotacaoService {

	@Autowired
	private PautaService pautaService;

	@Autowired
	private VotoService votoService;

	public ResultadoVotacao consultarResultado(Long pautaId) {
		Pauta pauta = pautaService.consultarPauta(pautaId);
		CompiladoVotos votosDTO = votoService.consultarVotosCompilados(pautaId);

		return ResultadoVotacao.builder().quantVotosComputados(votosDTO.getTotalVotos())
				.resultado(calculaResultado(votosDTO)).status(defineStatus(pauta)).build();
	}

	protected StatusVotacao defineStatus(Pauta pauta) {
		return pauta.isVotacaoEmAndamento() ? StatusVotacao.ABERTA : StatusVotacao.ENCERRADA;
	}

	protected StatusResultado calculaResultado(CompiladoVotos votosDTO) {
		if (votosDTO.getQuantVotosFavoraveis() > votosDTO.getQuantVotosContra()) {
			return StatusResultado.APROVADO;
		} else if (votosDTO.getQuantVotosFavoraveis() < votosDTO.getQuantVotosContra()) {
			return StatusResultado.REPROVADO;
		}
		return StatusResultado.EMPATE;
	}

}
