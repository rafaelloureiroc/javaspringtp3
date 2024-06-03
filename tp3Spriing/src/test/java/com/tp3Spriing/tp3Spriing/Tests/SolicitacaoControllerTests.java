package com.tp3Spriing.tp3Spriing.Tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp3Spriing.tp3Spriing.Controller.SolicitacaoController;
import com.tp3Spriing.tp3Spriing.Model.Solicitacao;
import com.tp3Spriing.tp3Spriing.Service.SolicitacaoService;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(SolicitacaoController.class)
public class SolicitacaoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SolicitacaoService solicitacaoService;

    @Test
    public void criarSolicitacaoTest() throws Exception {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setDescricao("Nova Solicitação");

        when(solicitacaoService.createSolicitacao(any(Solicitacao.class))).thenReturn(solicitacao);

        mockMvc.perform(post("/api/solicitacoes/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitacao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value(solicitacao.getDescricao()));
    }

    @Test
    public void listarSolicitacoesTest() throws Exception {
        List<Solicitacao> solicitacoes = getSolicitacoes();

        when(solicitacaoService.getAllSolicitacoes()).thenReturn(solicitacoes);

        mockMvc.perform(get("/api/solicitacoes/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].descricao").value("Solicitação 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].descricao").value("Solicitação 2"));
    }

    @Test
    public void obterSolicitacaoTest() throws Exception {
        Long id = 1L;
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(id);
        solicitacao.setDescricao("Solicitação Teste");

        when(solicitacaoService.getSolicitacaoById(id)).thenReturn(Optional.of(solicitacao));

        mockMvc.perform(get("/api/solicitacoes/listar/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(solicitacao.getId()))
                .andExpect(jsonPath("$.descricao").value(solicitacao.getDescricao()));
    }

    @Test
    public void atualizarSolicitacaoTest() throws Exception {
        Long id = 1L;
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setDescricao("Solicitação Atualizada");

        ArgumentCaptor<Solicitacao> solicitacaoCaptor = ArgumentCaptor.forClass(Solicitacao.class);
        when(solicitacaoService.updateSolicitacao(eq(id), solicitacaoCaptor.capture())).thenAnswer(invocation -> {
            Solicitacao solicitacaoAtualizada = solicitacaoCaptor.getValue();
            solicitacaoAtualizada.setId(id);
            return solicitacaoAtualizada;
        });

        mockMvc.perform(put("/api/solicitacoes/atualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.descricao").value(solicitacao.getDescricao()));
    }

    @Test
    public void deletarSolicitacaoTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/solicitacoes/deletar/{id}", id))
                .andExpect(status().isNoContent());
    }

    private static List<Solicitacao> getSolicitacoes() {
        Solicitacao solicitacao1 = new Solicitacao();
        solicitacao1.setId(1L);
        solicitacao1.setDescricao("Solicitação 1");

        Solicitacao solicitacao2 = new Solicitacao();
        solicitacao2.setId(2L);
        solicitacao2.setDescricao("Solicitação 2");

        return Arrays.asList(solicitacao1, solicitacao2);
    }
}