package com.cliente.crm.validacpfcnpj;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidaCPFTest {
	@Mock
	ValidaCPF validaCPF;

	@Test
    public void testValidaCPF() {
        when(validaCPF.isValide("85389473035")).thenReturn(true);
        when(validaCPF.isValide("11111111111")).thenReturn(false);

        assertTrue(validaCPF.isValide("85389473035"));
        assertFalse(validaCPF.isValide("11111111111"));
    }

	@Test
    public void testFormatarCPF() {
        when(validaCPF.formatarCPF("12345678901")).thenReturn("123.456");

        assertTrue(validaCPF.formatarCPF("12345678901").equals("123.456"));
    }
}
