package com.pc.cofipa.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.pc.cofipa.model.Produto;
import com.pc.cofipa.model.ItemSaida;




 class TabelaItensSaida {
	
	private String uuid;
	private List<ItemSaida> itens = new ArrayList<>();
	
	public TabelaItensSaida(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemSaida::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarItem(Produto produto, Integer quantidade) {
		Optional<ItemSaida> itemSaidaOptional = buscarItemPorProduto(produto);
		
		ItemSaida itemSaida = null;
		if(itemSaidaOptional.isPresent()) {
			itemSaida = itemSaidaOptional.get();
			itemSaida.setQuantidade(itemSaida.getQuantidade() + quantidade);
		}else {
			itemSaida = new ItemSaida();
			itemSaida.setProduto(produto);
			itemSaida.setQuantidade(quantidade);
			itemSaida.setValorUnitario(produto.getValorUnitario());
			itens.add(0, itemSaida);
		}
			
	}
	
	public void alterarQuantidadeItens(Produto produto, Integer quantidade){
		ItemSaida itemSaida = buscarItemPorProduto(produto).get();
		itemSaida.setQuantidade(quantidade);
	}
	
	public void excluirItem(Produto produto) {
		int indice = IntStream.range(0, itens.size())
				.filter(i -> itens.get(i).getProduto().equals(produto))
				.findAny().getAsInt();
		itens.remove(indice);		
	}
	
	public int total(){
		return itens.size();
	}

	public List<ItemSaida> getItens() {
		
		return itens;
	}
	
	private Optional<ItemSaida> buscarItemPorProduto(Produto produto) {
		return itens.stream()
		     .filter(i -> i.getProduto().equals(produto))
		     .findAny();
	
	}
	
	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaItensSaida other = (TabelaItensSaida) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
   
	 
}
