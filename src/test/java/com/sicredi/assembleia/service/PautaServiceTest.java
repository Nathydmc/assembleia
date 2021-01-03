package com.sicredi.assembleia.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Pauta;
import com.sicredi.assembleia.repository.PautaRepository;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

	private static final long ID_PAUTA = 1;

	@InjectMocks
	private PautaService service;

	@Mock
	private PautaRepository repository;

	@Test
	void deveLancarExcecaoQuandoPautaNaoEncontrada() {
		given(repository.findById(ID_PAUTA)).willReturn(Optional.empty());

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			service.consultarPauta(ID_PAUTA);
		});

		assertEquals("Pauta não encontrada.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		verify(repository, times(1)).findById(ID_PAUTA);
	}

	@Test
	void deveConsultarPauta() {
		given(repository.findById(ID_PAUTA)).willReturn(Optional.of(Pauta.builder().build()));

		Pauta pauta = service.consultarPauta(ID_PAUTA);

		assertNotNull(pauta);
		verify(repository, times(1)).findById(ID_PAUTA);
	}

	@Test
	void deveValidarPautaJaEncerrada() {
		Pauta pauta = Pauta.builder().votacaoEmAndamento(Boolean.FALSE).build();
		given(repository.findById(ID_PAUTA)).willReturn(Optional.of(pauta));

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			service.validarPauta(ID_PAUTA);
		});

		assertEquals("Votação da pauta já foi encerrada.", exception.getMessage());
		assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatus());
		verify(repository, times(1)).findById(ID_PAUTA);
	}

	@Test
	void deveValidarPautaEmAndamento() {
		Pauta pauta = Pauta.builder().votacaoEmAndamento(Boolean.TRUE).build();
		given(repository.findById(ID_PAUTA)).willReturn(Optional.of(pauta));

		assertDoesNotThrow(() -> {
			service.validarPauta(ID_PAUTA);
		});

		verify(repository, times(1)).findById(ID_PAUTA);
	}

	@Test
	void deveCadastrarPauta() {
		given(repository.save(any(Pauta.class))).willReturn(Pauta.builder().build());

		Pauta pauta = service.cadastrarPauta("Pauta teste");

		assertNotNull(pauta);
		verify(repository, times(1)).save(any(Pauta.class));
	}

}
