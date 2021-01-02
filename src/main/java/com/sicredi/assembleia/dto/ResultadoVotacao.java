package com.sicredi.assembleia.dto;

import com.sicredi.assembleia.enumerator.StatusResultado;
import com.sicredi.assembleia.enumerator.StatusVotacao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResultadoVotacao {

	private int quantVotosComputados;
	private StatusVotacao status;
	private StatusResultado resultado;

}
