package com.lluanps.task_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lluanps.task_manager.api.dto.TarefaDTO;
import com.lluanps.task_manager.api.exception.NotFoundException;
import com.lluanps.task_manager.model.Tarefa;
import com.lluanps.task_manager.repository.TarefaRepository;

@Service
public class TarefaService {
	
	@Autowired
	private TarefaRepository repository;

	public Tarefa buscarTarefaPorId(Integer id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("O id " + id + " não consta em nosso sistema"));
	}

	public Tarefa salvarTarefa(TarefaDTO t) {
		validarDataEntrega(t);
		Tarefa tarefa = mapearTarefa(t);
		return repository.save(tarefa);
	}

	public TarefaDTO atualizarTarefa(TarefaDTO t, Integer id) {
		buscarTarefaPorId(id);
		validarDataEntrega(t);
		
		Tarefa tarefa = buscarTarefaPorId(id);
		Optional.ofNullable(t.getDataEntrega()).ifPresent(tarefa::setDataEntrega);
		Optional.ofNullable(t.getNome()).ifPresent(tarefa::setNome);
		Optional.ofNullable(t.getResponsavel()).ifPresent(tarefa::setResponsavel);
		
		Tarefa save = repository.save(tarefa);
		return new TarefaDTO(save);
	}

	private void validarDataEntrega(TarefaDTO t) {
		if (t.getDataEntrega() != null  && t.getDataEntrega().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("A data de entrega não pode ser anterior a data atual");
		}
	}
	
	public List<TarefaDTO> buscarTodasTarefas() {
		return repository.findAll()
				.stream()
				.map(TarefaDTO::new)
				.toList();
	}

	public void deletarPorId(Integer id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException("O id " + id + " não consta em nosso sistema");
		}
		repository.deleteById(id);
	}

	private Tarefa mapearTarefa(TarefaDTO t) {
		Tarefa tarefa = new Tarefa();
		Optional.ofNullable(t.getDataEntrega()).ifPresent(tarefa::setDataEntrega);
		Optional.ofNullable(t.getNome()).ifPresent(tarefa::setNome);
		Optional.ofNullable(t.getResponsavel()).ifPresent(tarefa::setResponsavel);
		return tarefa;
	}
	
}
