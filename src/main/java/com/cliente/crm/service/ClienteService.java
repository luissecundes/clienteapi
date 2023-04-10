package com.cliente.crm.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cliente.crm.exception.ClienteNotFoundException;
import com.cliente.crm.model.Cliente;
import com.cliente.crm.model.Telefone;
import com.cliente.crm.repository.ClienteRepository;
import com.cliente.crm.repository.TelefoneRepository;
import com.cliente.crm.service.exceptions.DuplicidadeCpfCnpjException;
import com.cliente.crm.util.Situacao;
import com.cliente.crm.util.TipoPessoa;
import com.cliente.crm.validacpfcnpj.ValidaCNPJ;
import com.cliente.crm.validacpfcnpj.ValidaCPF;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private ValidaCPF validaCpf;

	@Autowired
	private ValidaCNPJ validaCnpj;

	public Page<Cliente> buscarClientes(String nome, Situacao situacao, LocalDate dataCadastro, TipoPessoa tipo,
			int pagina, int tamanhoPagina) {
		Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
		return clienteRepository.findByNomeContainingIgnoreCaseAndSituacaoAndDataDoCadastroAndTipo(nome, situacao,
				dataCadastro, tipo, pageable);
	}

	public Cliente salvar(Cliente cliente) throws DuplicidadeCpfCnpjException {
		if (Objects.isNull(cliente.getId())) {
			if (clienteRepository.existsByCpfCnpj(cliente.getCpfCnpj())) {
				throw new DuplicidadeCpfCnpjException("Já existe um cliente com o mesmo CPF ou CNPJ cadastrado.");
			}
			removeTodosCaracteresEspeciais(cliente);

			if (cliente.getTipo().compareTo(TipoPessoa.FISICA) == 0) {
				if (!validaCpf.isValide(cliente.getCpfCnpj())) {
					throw new DuplicidadeCpfCnpjException("CPF inválido.");
				}
			} else if (cliente.getTipo().compareTo(TipoPessoa.FISICA) == 0) {
				if (!validaCnpj.isCNPJ(cliente.getCpfCnpj())) {
					throw new DuplicidadeCpfCnpjException("CNPJ inválido.");
				}
			}

		}

		Cliente clienteSalvo = clienteRepository.save(cliente);
		cliente.getTelefones().forEach(c -> {
			c.setCliente(clienteSalvo);
			telefoneRepository.save(c);
		});
		return clienteSalvo;
	}

	private void removeTodosCaracteresEspeciais(Cliente cliente) {
		cliente.setCpfCnpj(cliente.getCpfCnpj().replace("/[^0-9 ]/g", ""));
	}

	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}

	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	public List<Cliente> buscarTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.parallelStream().map(c -> ordenaTelefone(c)).collect(Collectors.toList());

	}

	public Cliente atualizarTelefoneCliente(Long id, String telefone) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com id " + id));
		cliente.getTelefones().get(0).setTelefone(telefone);
		return clienteRepository.save(cliente);
	}

	private Cliente ordenaTelefone(Cliente cliente) {
		List<Telefone> telefones = cliente.getTelefones().stream()
				.sorted(Comparator.comparing(Telefone::getPrincipal, Comparator.reverseOrder()))
				.collect(Collectors.toList());
		cliente.setTelefones(telefones);
		return cliente;
	}
}
