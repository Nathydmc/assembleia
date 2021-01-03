package com.sicredi.assembleia.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.sicredi.assembleia.client.VoteAuthorizationClient;
import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Associado;
import com.sicredi.assembleia.repository.AssociadoRepository;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceTest {

	private final long ID_ASSOCIADO = 1;

	private final String CPF = "39796171066";

	private final String NOME = "Luisa";

	@InjectMocks
	private AssociadoService service;

	@Mock
	private AssociadoRepository repository;

	@Mock
	private VoteAuthorizationClient voteAuthorization;

	@Test
	void deveValidarAssociadoInexistente() {
		given(repository.findById(ID_ASSOCIADO)).willReturn(Optional.empty());

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			service.validarAssociado(ID_ASSOCIADO);
		});

		assertEquals("Associado não encontrado.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		verify(repository, times(1)).findById(ID_ASSOCIADO);
	}

	@Test
	void deveValidarAssociadoExistente() {
		given(repository.findById(ID_ASSOCIADO)).willReturn(Optional.of(Associado.builder().cpf(CPF).build()));

		assertDoesNotThrow(() -> {
			service.validarAssociado(ID_ASSOCIADO);
		});

		verify(repository, times(1)).findById(ID_ASSOCIADO);
		verify(voteAuthorization, times(1)).validarPorCpf(CPF);
	}

	@Test
	void deveCadastrarAssociado() {
		given(repository.existsByCpf(CPF)).willReturn(Boolean.FALSE);
		given(repository.save(Mockito.any(Associado.class))).willReturn(Associado.builder().build());

		Associado associado = service.cadastrarAssociado(CPF, NOME);

		assertNotNull(associado);
		verify(repository, times(1)).save(Mockito.any(Associado.class));
		verify(repository, times(1)).existsByCpf(CPF);
	}

	@Test
	void naoDeveCadastrarAssociadoQuandoCpfJaCadastrado() {
		given(repository.existsByCpf(CPF)).willReturn(Boolean.TRUE);

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			service.cadastrarAssociado(CPF, NOME);
		});

		assertEquals("Já existe um usuário com o cpf informado no sistema.", exception.getMessage());
		assertEquals(HttpStatus.NOT_ACCEPTABLE, exception.getStatus());
		verify(repository, times(0)).save(Mockito.any(Associado.class));
		verify(repository, times(1)).existsByCpf(CPF);
	}

}
