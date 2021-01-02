package com.sicredi.assembleia.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.assembleia.service.AssociadoService;

@RestController
@Validated
@RequestMapping("/associado")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;

	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity cadastrarAssociado(@RequestParam("cpf") @NotNull @NotBlank String cpf,
			@RequestParam("nome") @NotNull @NotBlank String nome) {
		return ResponseEntity.ok(associadoService.cadastrarAssociado(cpf, nome));
	}

}
