package com.pc.cofipa.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.pc.cofipa.model.Produto;
import com.pc.cofipa.session.TabelaItensSaida;

public class TabelaItensSaidaTest {
	
	private TabelaItensSaida tabelaItensSaida;
	
	@Before
	public void setUp(){
		this.tabelaItensSaida = new TabelaItensSaida("1");
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO,tabelaItensSaida.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {
		Produto produto = new Produto();
		BigDecimal valorUnitario = new BigDecimal("8.90");
		produto.setValorUnitario(valorUnitario);
		
		tabelaItensSaida.adicionarItem(produto, 1);
		
		assertEquals(valorUnitario, tabelaItensSaida.getValorTotal());
		
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		Produto p1 = new Produto();
		p1.setCodigo(1L);
		BigDecimal v1 = new BigDecimal("8.90");
		p1.setValorUnitario(v1);
		
		Produto p2 = new Produto();
		p2.setCodigo(2L);
		BigDecimal v2 = new BigDecimal("4.99");
		p2.setValorUnitario(v2);
		
		tabelaItensSaida.adicionarItem(p1, 1);
		tabelaItensSaida.adicionarItem(p2, 2);
		
		assertEquals(new BigDecimal("18.88"), tabelaItensSaida.getValorTotal());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmosProdutos() throws Exception {
		Produto p1 = new Produto();
		p1.setCodigo(1L);
		p1.setValorUnitario(new BigDecimal("4.50"));
		
		tabelaItensSaida.adicionarItem(p1, 1);
		tabelaItensSaida.adicionarItem(p1, 1);
		
		assertEquals(1, tabelaItensSaida.total());
		assertEquals(new BigDecimal("9.00"), tabelaItensSaida.getValorTotal());
		
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem() throws Exception {
		Produto p1 = new Produto();
		p1.setCodigo(1L);
		p1.setValorUnitario(new BigDecimal("4.50"));
		
		tabelaItensSaida.adicionarItem(p1, 1);
		tabelaItensSaida.alterarQuantidadeItens(p1, 3);
		
		assertEquals(1, tabelaItensSaida.total());
		assertEquals(new BigDecimal("13.50"), tabelaItensSaida.getValorTotal());
	}
	
	@Test
	public void deveExcluirItem() throws Exception {
		Produto p1 = new Produto();
		p1.setCodigo(1L);
		p1.setValorUnitario(new BigDecimal("8.90"));
		
		Produto p2 = new Produto();
		p2.setCodigo(2L);
		p2.setValorUnitario(new BigDecimal("4.99"));
		
		Produto p3 = new Produto();
		p3.setCodigo(3L);
		p3.setValorUnitario(new BigDecimal("2.00"));
		
		tabelaItensSaida.adicionarItem(p1, 1);
		tabelaItensSaida.adicionarItem(p2, 2);
		tabelaItensSaida.adicionarItem(p3, 1);
		
		tabelaItensSaida.excluirItem(p2);
		
		assertEquals(2, tabelaItensSaida.total());
		assertEquals(new BigDecimal("10.90"), tabelaItensSaida.getValorTotal());
	}

}
