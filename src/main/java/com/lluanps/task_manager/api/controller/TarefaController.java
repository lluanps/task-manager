package com.lluanps.task_manager.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lluanps.task_manager.api.dto.TarefaDTO;
import com.lluanps.task_manager.model.Tarefa;
import com.lluanps.task_manager.service.TarefaService;

@RestController
@RequestMapping("tarefas")
public class TarefaController {

	@Autowired
	private TarefaService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Tarefa salvarTarefa(@RequestBody TarefaDTO t)  {
		return service.salvarTarefa(t);
	}
	
	@PutMapping("/{id}")
	public TarefaDTO atualizarTarefa(@RequestBody TarefaDTO t, @PathVariable Integer id) {
		return service.atualizarTarefa(t, id);
	}
	
	@GetMapping
	public List<TarefaDTO> buscarTodasTarefas() {
		return service.buscarTodasTarefas();
	}
	
	@GetMapping("/{id}")
	public Tarefa buscarPorId(@PathVariable Integer id) {
		return service.buscarTarefaPorId(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Integer id) {
		service.deletarPorId(id);
	}
	
}
