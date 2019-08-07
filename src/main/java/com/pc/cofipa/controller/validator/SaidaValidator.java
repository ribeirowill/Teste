/*package com.pc.cofipa.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pc.cofipa.model.Saida;


@Component
public class SaidaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Saida.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "funcionario.codigo", "", "Selecione um funcionario na pesquisa rápida");
		
		Saida saida = (Saida) target;
		validarSeInformouItens(errors, saida);
		validarValorTotalNegativo(errors, saida);
	}

	private void validarValorTotalNegativo(Errors errors, Saida saida) {
		if (saida.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "Valor total não pode ser negativo");
		}
	}

	private void validarSeInformouItens(Errors errors, Saida saida) {
		if (saida.getItens().isEmpty()) {
			errors.reject("", "Adicione pelo menos um produto na saida");
		}
	}

}
*/