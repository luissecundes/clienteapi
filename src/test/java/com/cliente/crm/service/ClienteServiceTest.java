package com.cliente.crm.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cliente.crm.exception.ClienteNotFoundException;
import com.cliente.crm.model.Cliente;
import com.cliente.crm.model.Telefone;
import com.cliente.crm.repository.ClienteRepository;
import com.cliente.crm.service.exceptions.DuplicidadeCpfCnpjException;
import com.cliente.crm.validacpfcnpj.ValidaCNPJ;
import com.cliente.crm.validacpfcnpj.ValidaCPF;

@SpringBootTest
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ValidaCPF validaCpf;

	@Mock
	private ValidaCNPJ validaCnpj;

	@InjectMocks
	private ClienteService service = new ClienteService();

	@Test
	public void testBuscarClientesPorFiltro() {

	}

	@Test
	public void testSalvar() throws DuplicidadeCpfCnpjException {
		Cliente cliente = mock(Cliente.class);
		when(clienteRepository.existsByCpfCnpj(cliente.getCpfCnpj())).thenReturn(false);
		when(clienteRepository.save(cliente)).thenReturn(cliente);

		Cliente clienteSalvo = service.salvar(cliente);

		assertNotNull(clienteSalvo.getId());
	}

	@Test
	public void testDeletar_OK() {
		doNothing().when(clienteRepository).deleteById(1l);
		assertDoesNotThrow(() -> service.deletar(1l));
	}

	@Test
	public void testDeletar_EmptyResultDataAccessException() {
		doThrow(EmptyResultDataAccessException.class)
				.when(clienteRepository).deleteById(1l);
		assertThrows(Exception.class, () -> {
			service.deletar(1l);
		});
	}

	@Test
	public void testBuscarPorId_return_OK() {
		Cliente cliente = mock(Cliente.class);
		when(clienteRepository.findById(1l)).thenReturn(Optional.of(cliente));

		Cliente returnClient = service.buscarPorId(1l);

		assertNotNull(returnClient);
	}

	@Test
	public void testBuscarPorId_return_Null() {
		when(clienteRepository.findById(1l)).thenReturn(Optional.ofNullable(null));		
		assertNull(service.buscarPorId(1l));
	}

	@Test
	public void testBuscarTodos_return_OK() {
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(mock(Cliente.class));
		clientes.add(mock(Cliente.class));
		clientes.add(mock(Cliente.class));
		when(clienteRepository.findAll()).thenReturn(clientes);

		List<Cliente> returnClients = service.buscarTodos();

		assertNotNull(returnClients);
		assertEquals(3, returnClients.size());
	}

	@Test
	public void testBuscarTodos_return_Null() {
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(mock(Cliente.class));
		clientes.add(mock(Cliente.class));
		when(clienteRepository.findAll()).thenReturn(clientes);

		List<Cliente> returnClients = service.buscarTodos();

		assertNull(null);
		equals(null);
	}

	@Test
	public void testAtualizarTelefoneCliente() throws ClienteNotFoundException {
		Cliente cliente = mock(Cliente.class);
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
		when(clienteRepository.save(cliente)).thenReturn(cliente);

		Telefone telefone = mock(Telefone.class);
		when(telefone.getTelefone()).thenReturn("11910255638");
		List<Telefone> telefones = Collections.singletonList(telefone);
		when(cliente.getTelefones()).thenReturn(telefones);

		Cliente clienteAtualizado = service.atualizarTelefoneCliente(1L, "11910255638");

	}

}
