package com.pc.cofipa.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.pc.cofipa.controller.page.PageWrapper;
import com.pc.cofipa.model.Fornecedor;

import com.pc.cofipa.model.TipoPessoa;

import com.pc.cofipa.repository.Estados;
import com.pc.cofipa.repository.Fornecedores;
import com.pc.cofipa.repository.filter.FornecedorFilter;
import com.pc.cofipa.service.CadastroFornecedorService;
import com.pc.cofipa.service.exception.CpfCnpjFornecedorJaCadastradoException;

@Controller
@RequestMapping("/fornecedores")
public class FornecedoresController {

	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroFornecedorService cadastroFornecedorService;
	
	@Autowired
	private Fornecedores fornecedores;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Fornecedor fornecedor) {
		ModelAndView mv = new ModelAndView("fornecedor/CadastroFornecedor");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	@PostMapping({ "/novo","{\\+d}" })
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			return novo(fornecedor);
		}
		
		try {
			cadastroFornecedorService.salvar(fornecedor);
		} catch (CpfCnpjFornecedorJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj" , e.getMessage(), e.getMessage());
			return novo(fornecedor);
		}
		
		attributes.addFlashAttribute("mensagem" , "Fornecedor salvo com sucesso!");
		return new ModelAndView("redirect:/fornecedores/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(FornecedorFilter fornecedorFilter, BindingResult result
			, @PageableDefault(size = 5) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("fornecedor/PesquisaFornecedores");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		
		PageWrapper<Fornecedor> paginaWrapper = new PageWrapper<>(fornecedores.filtrar(fornecedorFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Fornecedor fornecedor = fornecedores.buscarComCidadeEstado(codigo);
		ModelAndView mv = novo(fornecedor);
		mv.addObject(fornecedor);
		return mv;
		
		
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Fornecedor> pesquisar(String nome){
		validarTamanhoNome(nome);
		return fornecedores.findByNomeStartingWithIgnoreCase(nome);
		
	}

	private void validarTamanhoNome(String nome) {
	    if(StringUtils.isEmpty(nome) || nome.length() < 3) {
	    	throw new IllegalArgumentException();
	    }
		
	}
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
	   return ResponseEntity.badRequest().build();
   }
	
   @DeleteMapping("/{codigo}")
  	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo){
  		cadastroFornecedorService.excluir(codigo);
  		return ResponseEntity.ok().build();
  	}	

}
