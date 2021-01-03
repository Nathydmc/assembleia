package com.sicredi.assembleia.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Voto;
import com.sicredi.assembleia.repository.VotoRepository;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

	private static final long ID_PAUTA = 1;

	private static final long ID_ASSOCIADO = 2;

	@InjectMocks
	private VotoService service;

	@Mock
	private VotoRepository repository;

	@Mock
	private PautaService pautaService;

	@Mock
	private AssociadoService associadoService;

	@Test
	void deveLancarExcecaoQuandoVotoJaComputado() {
		given(repository.existsByPautaIdAndAssociadoId(ID_PAUTA, ID_ASSOCIADO)).willReturn(Boolean.TRUE);

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			service.validarVotoJaComputado(ID_PAUTA, ID_ASSOCIADO);
		});

		assertEquals("Associado já votou na pauta.", exception.getMessage());
		assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatus());
		verify(repository, times(1)).existsByPautaIdAndAssociadoId(ID_PAUTA, ID_ASSOCIADO);
	}

	@Test
	void naoDeveLancarExcecaoQuandoVotoNaoComputado() {
		given(repository.existsByPautaIdAndAssociadoId(ID_PAUTA, ID_ASSOCIADO)).willReturn(Boolean.FALSE);

		assertDoesNotThrow(() -> service.validarVotoJaComputado(ID_PAUTA, ID_ASSOCIADO));
		verify(repository, times(1)).existsByPautaIdAndAssociadoId(ID_PAUTA, ID_ASSOCIADO);
	}

	@Test
	void deveSalvarVotoValido() {
		given(repository.existsByPautaIdAndAssociadoId(ID_PAUTA, ID_ASSOCIADO)).willReturn(Boolean.FALSE);
		given(repository.save(Mockito.any(Voto.class))).willReturn(Voto.builder().build());

		Voto voto = service.votar(ID_PAUTA, ID_ASSOCIADO, Boolean.TRUE);

		assertNotNull(voto);
		verify(pautaService, times(1)).validarPauta(ID_PAUTA);
		verify(associadoService, times(1)).validarAssociado(ID_ASSOCIADO);
		verify(repository, times(1)).save(Mockito.any());
	}

	@Test
	void naoDeveSalvarVotoInvalido() {
		given(repository.existsByPautaIdAndAssociadoId(ID_PAUTA, ID_ASSOCIADO)).willReturn(Boolean.TRUE);

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			service.votar(ID_PAUTA, ID_ASSOCIADO, Boolean.TRUE);
		});

		assertEquals("Associado já votou na pauta.", exception.getMessage());
		assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatus());
		verify(pautaService, times(1)).validarPauta(ID_PAUTA);
		verify(associadoService, times(1)).validarAssociado(ID_ASSOCIADO);
		verify(repository, times(0)).save(Mockito.any());
	}

	@Test
	void deveConsultarVotosCompilados() {
		service.consultarVotosCompilados(ID_PAUTA);
		
		verify(repository, times(1)).consultarVotosCompilados(ID_PAUTA);		
	}

}
