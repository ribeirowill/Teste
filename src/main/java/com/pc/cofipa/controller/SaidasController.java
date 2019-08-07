package com.pc.cofipa.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.pc.cofipa.model.Produto;
import com.pc.cofipa.model.Saida;
import com.pc.cofipa.repository.Departamentos;
import com.pc.cofipa.repository.Divisoes;
import com.pc.cofipa.repository.Produtos;
import com.pc.cofipa.repository.Secoes;
import com.pc.cofipa.security.UsuarioSistema;
import com.pc.cofipa.service.CadastroSaidaService;
import com.pc.cofipa.session.TabelasItensSession;





@Controller
@RequestMapping("/saidas")
public class SaidasController {
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Departamentos departamentos;
	
	@Autowired
	private Divisoes divisoes;
	
	@Autowired
	private Secoes secoes;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@Autowired
	private CadastroSaidaService cadastroSaidaService;
	

	
	@GetMapping("/nova")
	public ModelAndView nova(Saida saida) {
		ModelAndView mv = new ModelAndView("saida/CadastroSaida");
		mv.addObject("departamentos", departamentos.findAll());
		mv.addObject("divisoes", divisoes.findAll());
		mv.addObject("secoes", secoes.findAll());
		saida.setUuid(UUID.randomUUID().toString());
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(Saida saida, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		saida.setUsuario(usuarioSistema.getUsuario());
		saida.setItens(tabelaItens.getItens(saida.getUuid()));
		
		cadastroSaidaService.salvar(saida);
		attributes.addFlashAttribute("mensagem", "Saida salva com sucesso!");
		return new ModelAndView("redirect:/saidas/nova");
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoProduto, String uuid) {
		Produto produto = produtos.findOne(codigoProduto);
		tabelaItens.adicionarItem(uuid, produto, 1);
		return mvTabelaItensSaida(uuid);
		
	
	}
	
	@PutMapping("/item/{codigoProduto}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoProduto") Produto produto
			, Integer quantidade, String uuid){
		tabelaItens.alterarQuantidadeItens(uuid, produto, quantidade);
		return mvTabelaItensSaida(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{codigoProduto}")
	public ModelAndView excluirItem(@PathVariable("codigoProduto") Produto produto, @PathVariable String uuid) {
		tabelaItens.excluirItem(uuid, produto);
		return mvTabelaItensSaida(uuid);
	}

	private ModelAndView mvTabelaItensSaida(String uuid) {
		ModelAndView mv = new ModelAndView("saida/TabelaItensSaida");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}

}

