package com.pc.cofipa.service;




import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.cofipa.model.ItemSaida;
import com.pc.cofipa.model.Saida;
import com.pc.cofipa.repository.Saidas;

@Service
public class CadastroSaidaService {

	@Autowired
	private Saidas saidas;
	
	@Transactional
	public void salvar(Saida saida){
		if(saida.isNova()){
			saida.setDataCriacao(LocalDateTime.now());
			
			BigDecimal valorTotalItens = saida.getItens().stream()
					.map(ItemSaida::getValorTotal)
					.reduce(BigDecimal::add)
					.get();
			BigDecimal valorTotal = valorTotalItens
					.add(saida.getValorTotalItens());
					saida.setValorTotal(valorTotal);
		
		}
		saidas.save(saida);
	}
	
}	


