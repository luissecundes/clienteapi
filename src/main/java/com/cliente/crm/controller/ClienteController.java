package com.cliente.crm.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cliente.crm.model.Cliente;
import com.cliente.crm.service.ClienteService;
import com.cliente.crm.util.Situacao;
import com.cliente.crm.util.TipoPessoa;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public Page<Cliente> buscarClientes(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Situacao situacao,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro,
			@RequestParam(required = false) TipoPessoa tipo, @RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanhoPagina) {
		return clienteService.buscarClientes(nome, situacao, dataCadastro, tipo, pagina, tamanhoPagina);
	}

	@PostMapping
	public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteService.salvar(cliente);
		return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		Cliente cliente = clienteService.buscarPorId(id);
		if (cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(cliente, HttpStatus.OK);
		}
	}

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> buscarTodos() {
		List<Cliente> clientes = clienteService.buscarTodos();
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		clienteService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
		Cliente cliente = clienteService.buscarPorId(id);
		if (cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			clienteAtualizado.setId(id);
			Cliente clienteSalvo = clienteService.salvar(clienteAtualizado);
			return new ResponseEntity<>(clienteSalvo, HttpStatus.OK);
		}
	}

}
