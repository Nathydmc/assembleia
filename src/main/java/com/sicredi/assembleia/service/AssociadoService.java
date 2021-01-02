package com.sicredi.assembleia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Associado;
import com.sicredi.assembleia.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository associadoRepository;

	public Associado cadastrarAssociado(String cpf, String nome) {
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
		boolean associadoExistente = associadoRepository.existsById(associadoId);
		if (!associadoExistente) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Associado não encontrado.");
		}
	}

}
