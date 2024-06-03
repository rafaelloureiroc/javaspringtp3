package com.tp3Spriing.tp3Spriing.Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp3Spriing.tp3Spriing.Controller.FornecedorController;
import com.tp3Spriing.tp3Spriing.Model.Fornecedor;
import com.tp3Spriing.tp3Spriing.Service.FornecedorService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FornecedorService fornecedorService;

    @Test
    public void criarFornecedorTest() throws Exception {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Novo Fornecedor");
        fornecedor.setEndereco("Endereço do Novo Fornecedor");
        fornecedor.setTelefone("999999999");

        when(fornecedorService.createFornecedor(any(Fornecedor.class))).thenReturn(fornecedor);

        mockMvc.perform(post("/api/fornecedores/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(fornecedor.getNome()))
                .andExpect(jsonPath("$.endereco").value(fornecedor.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(fornecedor.getTelefone()));
    }

    @Test
    public void listarFornecedoresTest() throws Exception {
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setId(1L);
        fornecedor1.setNome("Fornecedor 1");
        fornecedor1.setEndereco("Endereço 1");
        fornecedor1.setTelefone("123456789");

        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setId(2L);
        fornecedor2.setNome("Fornecedor 2");
        fornecedor2.setEndereco("Endereço 2");
        fornecedor2.setTelefone("987654321");

        List<Fornecedor> fornecedores = Arrays.asList(fornecedor1, fornecedor2);

        when(fornecedorService.getAllFornecedores()).thenReturn(fornecedores);

        mockMvc.perform(get("/api/fornecedores/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Fornecedor 1"))
                .andExpect(jsonPath("$[0].endereco").value("Endereço 1"))
                .andExpect(jsonPath("$[0].telefone").value("123456789"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Fornecedor 2"))
                .andExpect(jsonPath("$[1].endereco").value("Endereço 2"))
                .andExpect(jsonPath("$[1].telefone").value("987654321"));
    }

    @Test
    public void obterFornecedorTest() throws Exception {
        Long id = 1L;
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        fornecedor.setNome("Fornecedor Teste");
        fornecedor.setEndereco("Endereço do Fornecedor Teste");
        fornecedor.setTelefone("888888888");

        when(fornecedorService.getFornecedorById(id)).thenReturn(Optional.of(fornecedor));

        mockMvc.perform(get("/api/fornecedores/listar/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fornecedor.getId()))
                .andExpect(jsonPath("$.nome").value(fornecedor.getNome()))
                .andExpect(jsonPath("$.endereco").value(fornecedor.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(fornecedor.getTelefone()));
    }

    @Test
    public void atualizarFornecedorTest() throws Exception {
        Long id = 1L;
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor Atualizado");
        fornecedor.setEndereco("Endereço do Fornecedor Atualizado");
        fornecedor.setTelefone("777777777");

        ArgumentCaptor<Fornecedor> fornecedorCaptor = ArgumentCaptor.forClass(Fornecedor.class);
        when(fornecedorService.updateFornecedor(eq(id), fornecedorCaptor.capture())).thenAnswer(invocation -> {
            Fornecedor fornecedorAtualizado = fornecedorCaptor.getValue();
            fornecedorAtualizado.setId(id);
            return fornecedorAtualizado;
        });

        mockMvc.perform(put("/api/fornecedores/atualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fornecedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(fornecedor.getNome()))
                .andExpect(jsonPath("$.endereco").value(fornecedor.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(fornecedor.getTelefone()));
    }
    @Test
    public void deletarFornecedorTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/fornecedores/deletar/{id}", id))
                .andExpect(status().isNoContent());
    }
}