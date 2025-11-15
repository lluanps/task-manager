package com.lluanps.task_manager.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lluanps.task_manager.api.dto.TarefaDTO;
import com.lluanps.task_manager.api.exception.NotFoundException;
import com.lluanps.task_manager.model.Tarefa;
import com.lluanps.task_manager.repository.TarefaRepository;
 class TarefaServiceTest {
	
	@Mock
	private TarefaRepository repository;

	@InjectMocks
	private TarefaService service;
	
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void deveBuscarTarefaPorId() {
    	Tarefa tarefa = new Tarefa();
    	tarefa.setId(1);
    	tarefa.setNome("tarefa teste");
    
    	when(repository.findById(1)).thenReturn(Optional.of(tarefa));
    	
    	Tarefa buscarTarefaPorId = service.buscarTarefaPorId(1);
    	
    	Assertions.assertNotNull(buscarTarefaPorId);
    	Assertions.assertEquals(1, buscarTarefaPorId.getId());
    	Assertions.assertEquals("tarefa teste", buscarTarefaPorId.getNome());
    }
    
    @Test
    void deveLancarExcecaoAoBuscarTarefaPorIdInexistente() {
    	when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
    	
    	Assertions.assertThrows(NotFoundException.class, () -> service.buscarTarefaPorId(99));
    }
	
    @Test
    void deveSalvarTarefaValida() {
    	TarefaDTO dto = new TarefaDTO();
    	dto.setNome("nome teste");
    	dto.setResponsavel("Luan");
    	dto.setDataEntrega(LocalDate.now().plusDays(7));
    	
        Tarefa entity = new Tarefa();
        entity.setId(10);
        entity.setNome("nome teste");
        
        when(repository.save(ArgumentMatchers.any(Tarefa.class))).thenReturn(entity);
        
        Tarefa salvarTarefa = service.salvarTarefa(dto);
        
        Assertions.assertNotNull(salvarTarefa);
        Assertions.assertEquals(10, salvarTarefa.getId());
        Assertions.assertEquals("nome teste", salvarTarefa.getNome());
    }
    
    @Test
    void deveLancarExcecaoAoSalvarTarefaComDataRetroativa() {
    	TarefaDTO tarefaDTO = new TarefaDTO();
    	tarefaDTO.setDataEntrega(LocalDate.now().minusDays(1));
    	
    	Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvarTarefa(tarefaDTO));
    }
    
    @Test
    void deveAtualizarTarefaValida() {
    	Tarefa tarefaExistente = new Tarefa();
    	tarefaExistente.setId(1);
    	tarefaExistente.setNome("teste antiga");
    	
    	when(repository.findById(1)).thenReturn(Optional.of(tarefaExistente));
    	when(repository.save(ArgumentMatchers.any(Tarefa.class))).thenReturn(tarefaExistente);
    	
    	TarefaDTO dto = new TarefaDTO();
    	dto.setNome("tarefa atualizada");
    	dto.setResponsavel("Luan");
    	
    	TarefaDTO atualizarTarefa = service.atualizarTarefa(dto, 1);
    	
    	Assertions.assertNotNull(atualizarTarefa);
    	Assertions.assertEquals("tarefa atualizada", atualizarTarefa.getNome());
    	Assertions.assertEquals("Luan", atualizarTarefa.getResponsavel());
    }

    @Test
    void deveLancarErroAoAtualizarTarefaComDataRetroativa() {
    	Tarefa tarefa = new Tarefa();
    	tarefa.setId(20);
    	
    	when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(tarefa));
    	
    	TarefaDTO dto = new TarefaDTO();
    	dto.setDataEntrega(LocalDate.now().minusDays(1));
    	
    	Assertions.assertThrows(IllegalArgumentException.class, () -> service.atualizarTarefa(dto, 20));
    }
    
    @Test
    void deveBuscarTodasTarefas() {
    	when(repository.findAll()).thenReturn(List.of(new Tarefa(), new Tarefa()));
    	
    	List<TarefaDTO> buscarTodasTarefas = service.buscarTodasTarefas();
    	
    	Assertions.assertEquals(2, buscarTodasTarefas.size());
    }

    @Test
    void deveDeletarPorId() {
    	when(repository.existsById(30)).thenReturn(true);
    	
    	service.deletarPorId(30);
    	
    	verify(repository).deleteById(30);
    }
    
    @Test
    void deveLancarErroAoDeletarTarefaInexistente() {
    	when(repository.existsById(ArgumentMatchers.anyInt())).thenReturn(false);
    	
    	Assertions.assertThrows(NotFoundException.class, () -> service.deletarPorId(9999));
    }
    
}