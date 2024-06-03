package com.tp3Spriing.tp3Spriing.Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp3Spriing.tp3Spriing.Controller.FuncionarioController;
import com.tp3Spriing.tp3Spriing.Model.Funcionario;
import com.tp3Spriing.tp3Spriing.Service.FuncionarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@WebMvcTest(FuncionarioController.class)
public class FuncionarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FuncionarioService funcionarioService;

    @Test
    public void criarFuncionarioTest() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Novo Funcionário");
        funcionario.setSalario(1000.0);
        funcionario.setCargo("Cargo do Novo Funcionário");

        when(funcionarioService.createFuncionario(any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(post("/api/funcionarios/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.salario").value(funcionario.getSalario()))
                .andExpect(jsonPath("$.cargo").value(funcionario.getCargo()));
    }

    @Test
    public void listarFuncionariosTest() throws Exception {
        List<Funcionario> funcionarios = getFuncionarios();

        when(funcionarioService.getAllFuncionarios()).thenReturn(funcionarios);

        mockMvc.perform(get("/api/funcionarios/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Funcionário 1"))
                .andExpect(jsonPath("$[0].salario").value(544.0))
                .andExpect(jsonPath("$[0].cargo").value("Cargo 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Funcionário 2"))
                .andExpect(jsonPath("$[1].salario").value(544.0))
                .andExpect(jsonPath("$[1].cargo").value("Cargo 2"));
    }

    @Test
    public void obterFuncionarioTest() throws Exception {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome("Funcionário Teste");
        funcionario.setSalario(1000.0);
        funcionario.setCargo("Cargo do Funcionário Teste");

        when(funcionarioService.getFuncionarioById(id)).thenReturn(Optional.of(funcionario));

        mockMvc.perform(get("/api/funcionarios/listar/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(funcionario.getId()))
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.salario").value(funcionario.getSalario()))
                .andExpect(jsonPath("$.cargo").value(funcionario.getCargo()));
    }

    @Test
    public void atualizarFuncionarioTest() throws Exception {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionário Atualizado");
        funcionario.setSalario(1500.0);
        funcionario.setCargo("Novo Cargo do Funcionário Atualizado");

        ArgumentCaptor<Funcionario> funcionarioCaptor = ArgumentCaptor.forClass(Funcionario.class);
        when(funcionarioService.updateFuncionario(eq(id), funcionarioCaptor.capture())).thenAnswer(invocation -> {
            Funcionario funcionarioAtualizado = funcionarioCaptor.getValue();
            funcionarioAtualizado.setId(id);
            return funcionarioAtualizado;
        });

        mockMvc.perform(put("/api/funcionarios/atualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.salario").value(funcionario.getSalario()))
                .andExpect(jsonPath("$.cargo").value(funcionario.getCargo()));
    }

    @Test
    public void deletarFuncionarioTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/funcionarios/deletar/{id}", id))
                .andExpect(status().isNoContent());
    }

    private static List<Funcionario> getFuncionarios() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId(1L);
        funcionario1.setNome("Funcionário 1");
        funcionario1.setSalario(544.0);
        funcionario1.setCargo("Cargo 1");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId(2L);
        funcionario2.setNome("Funcionário 2");
        funcionario2.setSalario(544.0);
        funcionario2.setCargo("Cargo 2");

        return Arrays.asList(funcionario1, funcionario2);
    }
}