package com.sicredi.assembleia.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.assembleia.dto.ResultadoVotacao;
import com.sicredi.assembleia.model.Pauta;
import com.sicredi.assembleia.service.PautaService;
import com.sicredi.assembleia.service.ResultadoVotacaoService;

@RestController
@Validated
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private ResultadoVotacaoService resultadoVotacaoService;

	@PostMapping
	public ResponseEntity<Pauta> cadastrarPauta(@RequestParam("assunto") @NotNull @NotEmpty String assunto) {
		return ResponseEntity.ok(pautaService.cadastrarPauta(assunto));
	}

	@GetMapping("/{pautaId}")
	public ResponseEntity<ResultadoVotacao> consultarResultado(@PathVariable("pautaId") @NotNull Long pautaId) {
		return ResponseEntity.ok(resultadoVotacaoService.consultarResultado(pautaId));
	}

}
