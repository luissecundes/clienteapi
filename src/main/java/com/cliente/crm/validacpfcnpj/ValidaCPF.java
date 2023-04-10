package com.cliente.crm.validacpfcnpj;

import org.springframework.stereotype.Component;

@Component
public class ValidaCPF {

	public boolean isValide(String cpf) {
		if (cpf == null || cpf.length() != 11) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 9; i++) {
			int digito = cpf.charAt(i) - '0';
			if (digito < 0 || digito > 9) {
				return false;
			}
			soma += digito * (10 - i);
		}

		int ultimoDigito = cpf.charAt(9) - '0';
		int penultimoDigito = cpf.charAt(10) - '0';

		int primeiroDigito = calcularDigitoVerificador(soma);
		if (primeiroDigito != ultimoDigito) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			int digito = cpf.charAt(i) - '0';
			soma += digito * (11 - i);
		}

		int segundoDigito = calcularDigitoVerificador(soma);
		return segundoDigito == penultimoDigito;
	}

	private int calcularDigitoVerificador(int soma) {
		int resto = soma % 11;
		if (resto < 2) {
			return 0;
		}
		return 11 - resto;
	}

	public String formatarCPF(String cpf) {
		if (cpf == null || cpf.length() != 11) {
			return "";
		}
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6);

	}
}
