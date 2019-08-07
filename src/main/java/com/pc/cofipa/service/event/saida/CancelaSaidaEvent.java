package com.pc.cofipa.service.event.saida;

import com.pc.cofipa.model.Saida;

public class CancelaSaidaEvent {

	private Saida saida;

	public CancelaSaidaEvent(Saida saida) {
		this.saida = saida;
	}

	public Saida getSaida() {
		return saida;
	}
	

}
