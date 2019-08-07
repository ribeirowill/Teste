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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pc.cofipa.controller.page.PageWrapper;
import com.pc.cofipa.dto.FuncionarioDTO;
import com.pc.cofipa.model.Funcionario;

import com.pc.cofipa.repository.Funcionarios;
import com.pc.cofipa.repository.filter.FuncionarioFilter;

import com.pc.cofipa.service.CadastroFuncionarioService;
import com.pc.cofipa.service.exception.ImpossivelExcluirEntidadeException;
import com.pc.cofipa.service.exception.NomeFuncionarioJaCadastradoException;

@Controller
@RequestMapping("/funcionarios")
public class FuncionariosController {
	
	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	private Funcionarios funcionarios;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("funcionario/CadastroFuncionario");
		return mv;
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(funcionario);
		}
		
		try {
		cadastroFuncionarioService.salvar(funcionario);
		} catch (NomeFuncionarioJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(funcionario);
		}
		attributes.addFlashAttribute("mensagem", "Funcionario salvo com sucesso!");
		return new ModelAndView("redirect:/funcionarios/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(FuncionarioFilter funcionarioFilter, BindingResult result
			, @PageableDefault(size = 5)Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("funcionario/PesquisaFuncionarios");
	
		
		PageWrapper<Funcionario> paginaWrapper = new PageWrapper<>(funcionarios.filtrar(funcionarioFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<FuncionarioDTO> buscar(String nomeOuRg) {
		validarTamanhoNome(nomeOuRg);
		return funcionarios.porNomeOuRg(nomeOuRg);
	}
	
	private void validarTamanhoNome(String nomeOuRg) {
	    if(StringUtils.isEmpty(nomeOuRg) || nomeOuRg.length() < 3) {
	    	throw new IllegalArgumentException();
	    }
		
	}
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
	   return ResponseEntity.badRequest().build();
   }
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Funcionario funcionario) {
		try {
		cadastroFuncionarioService.excluir(funcionario);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Funcionario funcionario) {
		ModelAndView mv = novo(funcionario);
		mv.addObject(funcionario);
		
		return mv;
	}
	

}
