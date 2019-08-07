package com.pc.cofipa.service.event.saida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pc.cofipa.model.ItemSaida;
import com.pc.cofipa.model.Produto;
import com.pc.cofipa.repository.Produtos;



@Component
public class SaidaListiner {
	
	@Autowired
	private Produtos produtos;
	
	@EventListener
	public void saidaEmitida(SaidaEvent saidaEvent){
		for (ItemSaida item : saidaEvent.getSaida().getItens()){
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setEstoque(produto.getEstoque() - item.getQuantidade());
			produtos.save(produto);
		}
	}
	
	
	@EventListener
	public void saidaCancelada(CancelaSaidaEvent cancelaSaidaEvent){
		for (ItemSaida item : cancelaSaidaEvent.getSaida().getItens()){
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setEstoque(produto.getEstoque() + item.getQuantidade());
			produtos.save(produto);
		}
	}

}


