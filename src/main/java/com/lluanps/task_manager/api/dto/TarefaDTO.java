package com.lluanps.task_manager.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lluanps.task_manager.api.config.MultiDateDeserializer;
import com.lluanps.task_manager.model.Tarefa;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TarefaDTO {

	private Integer id;
	private String nome;
	private String responsavel;
	
	@JsonDeserialize(using = MultiDateDeserializer.class)
	private LocalDate dataEntrega;
	
	public TarefaDTO(Tarefa tarefa) {
		this.id = tarefa.getId();
		this.nome = tarefa.getNome();
		this.dataEntrega = tarefa.getDataEntrega();
		this.responsavel = tarefa.getResponsavel();
	}
	
}
