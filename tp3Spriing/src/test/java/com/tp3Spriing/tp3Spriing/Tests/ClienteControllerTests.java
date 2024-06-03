package com.tp3Spriing.tp3Spriing.Tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp3Spriing.tp3Spriing.Controller.ClienteController;
import com.tp3Spriing.tp3Spriing.Model.Cliente;
import com.tp3Spriing.tp3Spriing.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void criarClienteTest() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Novo Cliente");
        cliente.setEmail("novoCliente@example.com");
        cliente.setTelefone("999999999");

        when(clienteService.createCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(cliente.getNome()))
                .andExpect(jsonPath("$.email").value(cliente.getEmail()))
                .andExpect(jsonPath("$.telefone").value(cliente.getTelefone()));
    }

    @Test
    public void ListarClienteTest() throws Exception {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Cliente Teste");
        cliente.setEmail("clienteTeste@example.com");
        cliente.setTelefone("888888888");

        when(clienteService.getClienteById(id)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/listar/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()))
                .andExpect(jsonPath("$.email").value(cliente.getEmail()))
                .andExpect(jsonPath("$.telefone").value(cliente.getTelefone()));
    }

    @Test
    public void atualizarClienteTest() throws Exception {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Atualizado");
        cliente.setEmail("clienteAtualizado@example.com");
        cliente.setTelefone("777777777");

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        when(clienteService.updateCliente(eq(id), clienteCaptor.capture())).thenAnswer(invocation -> {
            Cliente clienteAtualizado = clienteCaptor.getValue();
            clienteAtualizado.setId(id);
            return clienteAtualizado;
        });

        mockMvc.perform(put("/api/clientes/atualizar/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()))
                .andExpect(jsonPath("$.email").value(cliente.getEmail()))
                .andExpect(jsonPath("$.telefone").value(cliente.getTelefone()));
    }


    @Test
    public void deletarClienteTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/clientes/deletar/{id}", id))
                .andExpect(status().isNoContent());
    }
}
