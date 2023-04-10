package com.cliente.crm.validacpfcnpj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidaCNPJTest {

	@InjectMocks
	private ValidaCNPJ validaCNPJ;

	@Test
	public void testValidaCNPJ() {
		assertTrue(validaCNPJ.isCNPJ("67997691000110"));
		assertFalse(validaCNPJ.isCNPJ("01234567000188"));
	}

	@Test
	public void testImprimeCNPJ() {
		String cnpj = "01234567000189";
		String cnpjFormatado = "01.234.567/0001-89";
		assertEquals(cnpjFormatado, validaCNPJ.imprimeCNPJ(cnpj));
	}
}
