package com.tp3Spriing.tp3Spriing;

import com.tp3Spriing.tp3Spriing.Tests.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {Tp3SpriingApplication.class,
		ProdutoControllerTests.class,
		ClienteControllerTests.class,
		FuncionarioControllerTests.class,
		SolicitacaoControllerTests.class,
		FornecedorControllerTests.class})

class Tp3SpriingApplicationTests {

	@Autowired
	private ProdutoControllerTests produtoControllerTests;

	@Autowired
	private ClienteControllerTests clienteControllerTests;

	@Autowired
	private FuncionarioControllerTests funcionarioControllerTests;

	@Autowired
	private SolicitacaoControllerTests solicitacaoControllerTests;

	@Autowired
	private FornecedorControllerTests fornecedorControllerTests;

	@Test
	void contextLoads() {
	}

	@Test
	void testProdutoController() throws Exception {
		produtoControllerTests.criarProdutoTest();
		produtoControllerTests.listarProdutosTest();
		produtoControllerTests.atualizarProdutoTest();
		produtoControllerTests.deletarProdutoTest();

	}

	@Test
	void testClienteController() throws Exception {
		clienteControllerTests.criarClienteTest();
		clienteControllerTests.ListarClienteTest();
		clienteControllerTests.atualizarClienteTest();
		clienteControllerTests.deletarClienteTest();
	}

	@Test
	void testFuncionarioController() throws Exception {
		funcionarioControllerTests.criarFuncionarioTest();
		funcionarioControllerTests.listarFuncionariosTest();
		funcionarioControllerTests.atualizarFuncionarioTest();
		funcionarioControllerTests.deletarFuncionarioTest();
	}

	@Test
	void testSolicitacaoController() throws Exception {
		solicitacaoControllerTests.criarSolicitacaoTest();
		solicitacaoControllerTests.listarSolicitacoesTest();
		solicitacaoControllerTests.atualizarSolicitacaoTest();
		solicitacaoControllerTests.deletarSolicitacaoTest();
	}

	@Test
	void testFornecedorController() throws Exception {
		fornecedorControllerTests.criarFornecedorTest();
		fornecedorControllerTests.listarFornecedoresTest();
		fornecedorControllerTests.atualizarFornecedorTest();
		fornecedorControllerTests.deletarFornecedorTest();
	}
}
