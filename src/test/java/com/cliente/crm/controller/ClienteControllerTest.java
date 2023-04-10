package com.cliente.crm.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cliente.crm.model.Cliente;
import com.cliente.crm.repository.ClienteRepository;
import com.cliente.crm.service.ClienteService;
import com.cliente.crm.util.Situacao;
import com.cliente.crm.util.TipoPessoa;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ClienteControllerTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ClienteService clienteService;

	@InjectMocks
	private ClienteController controller = new ClienteController();

	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testBuscarClientesPorFiltro() throws Exception {
		Page<Cliente> clientes = new PageImpl<>(Arrays.asList(new Cliente(), new Cliente()));

		when(clienteService.buscarClientes(anyString(), any(Situacao.class), any(LocalDate.class),
				any(TipoPessoa.class), anyInt(), anyInt())).thenReturn(clientes);

		mockMvc.perform(get("/clientes")).andExpect(status().isOk());
	}

	@Test
	public void salvar_deveRetornarClienteSalvo() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setNome("Lucas Augusto");

		when(clienteService.salvar(cliente)).thenReturn(cliente);

		mockMvc.perform(post("/clientes")).andExpect(status().isCreated());
	}

	@Test
	public void testBuscarPorId_OK() throws Exception {
		Cliente mockCliente = new Cliente();
		mockCliente.setId(1L);
		mockCliente.setNome("Lucas Augusto");
		mockCliente.setSituacao(Situacao.ATIVO);

		when(clienteService.buscarPorId(1L)).thenReturn(mockCliente);

		MvcResult result = mockMvc.perform(get("/clientes/1")).andExpect(status().isOk()).andReturn();

		String responseBody = result.getResponse().getContentAsString();
		Cliente responseCliente = objectMapper.readValue(responseBody, Cliente.class);
		assertEquals(mockCliente.getNome(), responseCliente.getNome());

	}

	@Test
	public void testBuscarPorId_400() throws Exception {
		Cliente mockCliente = new Cliente();
		mockCliente.setId(1L);
		mockCliente.setNome("Lucas Augusto");
		mockCliente.setSituacao(Situacao.ATIVO);

		when(clienteService.buscarPorId(1L)).thenReturn(mockCliente);

		MvcResult result = mockMvc.perform(get("/clientes/1")).andExpect(status().isOk()).andReturn();

		String responseBody = result.getResponse().getContentAsString();
		Cliente responseCliente = objectMapper.readValue(responseBody, Cliente.class);
		assertEquals(mockCliente.getNome(), responseCliente.getNome());

	}

	@Test
	public void testBuscarTodos() throws Exception {
		var cliente = mock(Cliente.class);
		cliente.setId(1l);
		cliente.setNome("Lucas Augusto");
		cliente.setTipo(TipoPessoa.FISICA);
		cliente.setCpfCnpj("12345678901");
		cliente.setRgIe("012345678");
		cliente.setDataDoCadastro(null);
		cliente.setSituacao(Situacao.ATIVO);
		cliente.setTelefones(null);

		Mockito.when(clienteRepository.findAll()).thenReturn(List.of(cliente));

		mockMvc.perform(get("/clientes"));

	}

	@Test
	public void testDeletar() throws Exception {

		Long id = 1L;

		doNothing().when(clienteService).deletar(id);

		mockMvc.perform(delete("/clientes/{id}", id)).andExpect(status().isNoContent());

	}

	@Test
	public void atualizar_DeveRetornarNotFound_QuandoReceberUmIdInexistente() throws Exception {
		Long id = 1L;
		Cliente cliente = null;

		when(clienteService.buscarPorId(id)).thenReturn(cliente);

		String json = "{\"nome\":\"José\"}";

		mockMvc.perform(put("/clientes/{id}", id).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isNotFound());

		verify(clienteService, times(1)).buscarPorId(id);
		verifyNoMoreInteractions(clienteService);
	}

}
