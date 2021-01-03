package com.sicredi.assembleia.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sicredi.assembleia.client.VoteAuthorizationClient;
import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Associado;
import com.sicredi.assembleia.repository.AssociadoRepository;

import br.com.caelum.stella.validation.CPFValidator;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private VoteAuthorizationClient voteAuthorization;
	
	private CPFValidator cpfValidator = new CPFValidator();

	public Associado cadastrarAssociado(String cpf, String nome) {
		cpfValidator.assertValid(cpf);
		if (isCpfCadastrado(cpf)) {
			throw new BusinessException(HttpStatus.NOT_ACCEPTABLE,
					"Já existe um usuário com o cpf informado no sistema.");
		}
		return associadoRepository.save(Associado.builder().cpf(cpf).nome(nome).build());
	}

	private boolean isCpfCadastrado(String cpf) {
		return associadoRepository.existsByCpf(cpf);
	}

	public void validarAssociado(Long associadoId) {
		Associado associado = associadoRepository.findById(associadoId).orElse(null);
		if (Objects.isNull(associado)) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Associado não encontrado.");
		}
		voteAuthorization.validarPorCpf(associado.getCpf());
	}

}
