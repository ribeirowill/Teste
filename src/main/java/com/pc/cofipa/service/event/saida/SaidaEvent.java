package com.pc.cofipa.service.event.saida;

import com.pc.cofipa.model.Saida;

public class SaidaEvent {

	private Saida saida;

	public SaidaEvent(Saida saida) {
		this.saida = saida;
	}

	public Saida getSaida() {
		return saida;
	}
	
	

}
