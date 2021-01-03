package com.sicredi.assembleia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sicredi.assembleia.dto.CompiladoVotos;
import com.sicredi.assembleia.dto.ResultadoVotacao;
import com.sicredi.assembleia.enumerator.StatusResultado;
import com.sicredi.assembleia.enumerator.StatusVotacao;
import com.sicredi.assembleia.model.Pauta;

import lombok.Data;

@ExtendWith(MockitoExtension.class)
class ResultadoVotacaoServiceTest {

	private static final long ID_PAUTA = 1;

	@InjectMocks
	private ResultadoVotacaoService service;

	@Mock
	private PautaService pautaService;

	@Mock
	private VotoService votoService;

	@Test
	void deveRetornarResultadoAprovadoQuandoMaioriaDosVotosFavoraveis() {
		CompiladoVotosDTO compiladoVotos = new CompiladoVotosDTO(10, 6, 4);

		StatusResultado statusResultado = service.calculaResultado(compiladoVotos);

		assertEquals(StatusResultado.APROVADO, statusResultado);
	}

	@Test
	void deveRetornarResultadoReprovadoQuandoMaioriaDosVotosContra() {
		CompiladoVotosDTO compiladoVotos = new CompiladoVotosDTO(10, 4, 6);

		StatusResultado statusResultado = service.calculaResultado(compiladoVotos);

		assertEquals(StatusResultado.REPROVADO, statusResultado);
	}

	@Test
	void deveRetornarResultadoEmpateQuandoVotosContraIgualVotosFavoraveis() {
		CompiladoVotosDTO compiladoVotos = new CompiladoVotosDTO(10, 5, 5);

		StatusResultado statusResultado = service.calculaResultado(compiladoVotos);

		assertEquals(StatusResultado.EMPATE, statusResultado);
	}

	@Test
	void deveDefinirStatusAbertaQuandoVotacaoEmAndamento() {
		Pauta pauta = Pauta.builder().votacaoEmAndamento(Boolean.TRUE).build();

		assertEquals(StatusVotacao.ABERTA, service.defineStatus(pauta));
	}

	@Test
	void deveDefinirStatusEncerradaQuandoVotacaoEncerrada() {
		Pauta pauta = Pauta.builder().votacaoEmAndamento(Boolean.FALSE).build();

		assertEquals(StatusVotacao.ENCERRADA, service.defineStatus(pauta));
	}

	@Test
	void deveConsultarResultadoVotacaoPauta() {
		Pauta pauta = Pauta.builder().votacaoEmAndamento(Boolean.FALSE).build();
		CompiladoVotosDTO compiladoVotos = new CompiladoVotosDTO(10, 7, 3);

		given(pautaService.consultarPauta(ID_PAUTA)).willReturn(pauta);
		given(votoService.consultarVotosCompilados(ID_PAUTA)).willReturn(compiladoVotos);

		ResultadoVotacao resultado = service.consultarResultado(ID_PAUTA);
		
		verify(pautaService, times(1)).consultarPauta(ID_PAUTA);
		verify(votoService, times(1)).consultarVotosCompilados(ID_PAUTA);
		assertNotNull(resultado);
		assertEquals(StatusResultado.APROVADO, resultado.getResultado());
		assertEquals(StatusVotacao.ENCERRADA, resultado.getStatus());
		assertEquals(10, resultado.getQuantVotosComputados());
	}

	@Data
	private class CompiladoVotosDTO implements CompiladoVotos {

		private int totalVotos;
		private int quantVotosFavoraveis;
		private int quantVotosContra;

		public CompiladoVotosDTO(int totalVotos, int quantVotosFavoraveis, int quantVotosContra) {
			super();
			this.totalVotos = totalVotos;
			this.quantVotosFavoraveis = quantVotosFavoraveis;
			this.quantVotosContra = quantVotosContra;
		}

	}

}
