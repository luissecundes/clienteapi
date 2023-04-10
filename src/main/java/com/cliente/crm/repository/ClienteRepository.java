package com.cliente.crm.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cliente.crm.model.Cliente;
import com.cliente.crm.util.Situacao;
import com.cliente.crm.util.TipoPessoa;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByCpfCnpj(String cpfCnpj);

	Page<Cliente> findByNomeContainingIgnoreCaseAndSituacaoAndDataDoCadastroAndTipo(String nome, Situacao situacao,
			LocalDate dataDoCadastro, TipoPessoa tipo, Pageable pageable);

}
