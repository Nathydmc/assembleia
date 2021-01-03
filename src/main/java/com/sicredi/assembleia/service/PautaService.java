package com.sicredi.assembleia.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sicredi.assembleia.exception.BusinessException;
import com.sicredi.assembleia.model.Pauta;
import com.sicredi.assembleia.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta cadastrarPauta(String assunto) {
		Pauta pauta = pautaRepository.save(Pauta.builder().assunto(assunto).inicioSessao(LocalDateTime.now())
				.votacaoEmAndamento(Boolean.TRUE).build());
		agendarEncerramentoPauta(pauta);
		return pauta;
	}

	private void agendarEncerramentoPauta(Pauta pauta) {
		TimerTask task = new TimerTask() {
			public void run() {
				encerrarPauta(pauta);
				System.out.println("Task performed on: " + new Date() + "n" + "Thread's name: "
						+ Thread.currentThread().getName());
			}
		};
		Timer timer = new Timer("Encerramento da Pauta de id " + pauta.getId());

		long delay = 60000L;
		timer.schedule(task, delay);
	}

	protected void encerrarPauta(Pauta pauta) {
		pauta.setVotacaoEmAndamento(Boolean.FALSE);
		pautaRepository.save(pauta);
	}
	
	public void validarPauta(Long pautaId) {
		Pauta pauta = consultarPauta(pautaId);
		if (!pauta.isVotacaoEmAndamento()) {
			throw new BusinessException(HttpStatus.NOT_ACCEPTABLE, "Votação da pauta já foi encerrada.");
		}
	}

	public Pauta consultarPauta(Long pautaId) {
		Pauta pauta = pautaRepository.findById(pautaId).orElse(null);
		if (Objects.isNull(pauta)) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Pauta não encontrada.");
		}
		return pauta;
	}

}
