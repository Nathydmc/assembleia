package com.sicredi.assembleia.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sicredi.assembleia.dto.AutorizacaoCpf;
import com.sicredi.assembleia.exception.BusinessException;

@Component
public class VoteAuthorizationClient {

	public void validarPorCpf(String cpf) {
		RestTemplate restTemplate = new RestTemplate();
		AutorizacaoCpf result = null;
		try {
			result = restTemplate.getForObject(String.format("https://user-info.herokuapp.com/users/%s", cpf),
					AutorizacaoCpf.class);
		} catch (HttpClientErrorException.NotFound e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "O cpf informado é inválido.");
		}
		if ("UNABLE_TO_VOTE".equals(result.getStatus())) {
			throw new BusinessException(HttpStatus.NOT_ACCEPTABLE, "O associado não está apto a votar.");
		}
	}

}
