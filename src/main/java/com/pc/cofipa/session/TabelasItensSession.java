package com.pc.cofipa.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.pc.cofipa.model.Produto;
import com.pc.cofipa.model.ItemSaida;


@SessionScope
@Component
public class TabelasItensSession {
	
	private Set<TabelaItensSaida> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Produto produto, int quantidade) {
		TabelaItensSaida tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarItem(produto, quantidade);
		tabelas.add(tabela);
	}

	public void alterarQuantidadeItens(String uuid, Produto produto, Integer quantidade) {
		TabelaItensSaida tabela = buscarTabelaPorUuid(uuid);
		tabela.alterarQuantidadeItens(produto, quantidade);
		
	}

	public void excluirItem(String uuid, Produto produto) {
		TabelaItensSaida tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirItem(produto);
		
	}

	public List<ItemSaida> getItens(String uuid) {
	     return buscarTabelaPorUuid(uuid).getItens();
	}
	
	public Object getValorTotal(String uuid) {
		return buscarTabelaPorUuid(uuid).getValorTotal();
	}
	
	private TabelaItensSaida buscarTabelaPorUuid(String uuid) {
		TabelaItensSaida tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny().orElse(new TabelaItensSaida(uuid));
		return tabela;
	}



}
