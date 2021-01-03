package com.sicredi.assembleia.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.assembleia.model.Voto;
import com.sicredi.assembleia.service.VotoService;

@RestController
@Validated
@RequestMapping("/voto")
public class VotoController {

	@Autowired
	private VotoService votoService;

	@PostMapping
	public ResponseEntity<Voto> votar(@RequestParam("pautaId") @NotNull Long pautaId,
			@RequestParam("associadoId") @NotNull Long associadoId, @RequestParam("voto") @NotNull boolean voto) {
		return ResponseEntity.ok(votoService.votar(pautaId, associadoId, voto));
	}

}
