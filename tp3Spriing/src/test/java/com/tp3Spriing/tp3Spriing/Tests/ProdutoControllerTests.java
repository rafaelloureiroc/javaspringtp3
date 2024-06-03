package com.tp3Spriing.tp3Spriing.Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp3Spriing.tp3Spriing.Controller.ProdutoController;
import com.tp3Spriing.tp3Spriing.Model.Produto;
import com.tp3Spriing.tp3Spriing.Service.ProdutoService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;

    @Test
    public void criarProdutoTest() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Novo Produto");
        produto.setDescricao("Descrição do Novo Produto");
        produto.setPreco(99.99);

        when(produtoService.createProduto(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/api/produtos/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(produto.getNome()))
                .andExpect(jsonPath("$.descricao").value(produto.getDescricao()))
                .andExpect(jsonPath("$.preco").value(produto.getPreco()));
    }

    @Test
    public void listarProdutosTest() throws Exception {
        List<Produto> produtos = getProdutos();

        when(produtoService.getAllProdutos()).thenReturn(produtos);

        mockMvc.perform(get("/api/produtos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].descricao").value("Descrição 1"))
                .andExpect(jsonPath("$[0].preco").value(100.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"))
                .andExpect(jsonPath("$[1].descricao").value("Descrição 2"))
                .andExpect(jsonPath("$[1].preco").value(200.0));
    }

    @Test
    public void obterProdutoTest() throws Exception {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");
        produto.setPreco(99.99);

        when(produtoService.getProdutoById(id)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/api/produtos/listar/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produto.getId()))
                .andExpect(jsonPath("$.nome").value(produto.getNome()))
                .andExpect(jsonPath("$.descricao").value(produto.getDescricao()))
                .andExpect(jsonPath("$.preco").value(produto.getPreco()));
    }

    @Test
    public void atualizarProdutoTest() throws Exception {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setNome("Produto Atualizado");
        produto.setDescricao("Nova Descrição do Produto Atualizado");
        produto.setPreco(199.99);

        ArgumentCaptor<Produto> produtoCaptor = ArgumentCaptor.forClass(Produto.class);
        when(produtoService.updateProduto(eq(id), produtoCaptor.capture())).thenAnswer(invocation -> {
            Produto produtoAtualizado = produtoCaptor.getValue();
            produtoAtualizado.setId(id);
            return produtoAtualizado;
        });

        mockMvc.perform(put("/api/produtos/atualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(produto.getNome()))
                .andExpect(jsonPath("$.descricao").value(produto.getDescricao()))
                .andExpect(jsonPath("$.preco").value(produto.getPreco()));
    }

    @Test
    public void deletarProdutoTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/produtos/deletar/{id}", id))
                .andExpect(status().isNoContent());
    }

    private static List<Produto> getProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setDescricao("Descrição 1");
        produto1.setPreco(100.0);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto 2");
        produto2.setDescricao("Descrição 2");
        produto2.setPreco(200.0);

        return Arrays.asList(produto1, produto2);
    }
}