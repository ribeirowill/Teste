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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pc.cofipa.controller.page.PageWrapper;
import com.pc.cofipa.model.MaterialInformatica;

import com.pc.cofipa.repository.Fornecedores;
import com.pc.cofipa.repository.ItensMateriaisInformatica;
import com.pc.cofipa.repository.MateriaisInformatica;

import com.pc.cofipa.repository.Unidades;
import com.pc.cofipa.repository.filter.MaterialInformaticaFilter;

import com.pc.cofipa.service.CadastroMaterialInformaticaService;
import com.pc.cofipa.service.exception.ImpossivelExcluirEntidadeException;


@Controller
@RequestMapping("/materiaisInformatica")
public class MateriaisInformaticaController {
	
	@Autowired
	private CadastroMaterialInformaticaService cadastroMaterialInformaticaService;
	
	@Autowired
	private Unidades unidades;
	
	@Autowired
	public Fornecedores fornecedores;
	
	@Autowired
	private ItensMateriaisInformatica itensMateriaisInformatica;
		
	@Autowired
	private MateriaisInformatica materiaisInformatica;
	
	
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(MaterialInformatica materialInformatica) {
		ModelAndView mv = new ModelAndView("materiaisInformatica/CadastroMateriaisInformatica");
		mv.addObject("unidades", unidades.findAll());
		mv.addObject("fornecedores", fornecedores.findAll());
		mv.addObject("itensMateriaisInformatica", itensMateriaisInformatica.findAll());
		return mv;
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<MaterialInformatica> pesquisaPorCodigoItemMaterialInformatica(
		   @RequestParam(name = "itemMaterialInformatica", defaultValue = "-1" )Long codigoItemMaterialInformatica) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		return materiaisInformatica.findByItemMaterialInformaticaCodigo(codigoItemMaterialInformatica);
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid MaterialInformatica materialInformatica, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			//throw new RuntimeException();
			return novo(materialInformatica);
		}
		
		
		cadastroMaterialInformaticaService.salvar(materialInformatica);
		attributes.addFlashAttribute("mensagem","Material Informatica salvo com sucesso!");
		return new ModelAndView("redirect:/materiaisInformatica/novo");
				
	}
	
	@GetMapping
	public ModelAndView pesquisar(MaterialInformaticaFilter materialInformaticaFilter, BindingResult result
			, @PageableDefault(size = 5)Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("materiaisInformatica/PesquisaMateriaisInformatica");
		mv.addObject("unidades", unidades.findAll());
		mv.addObject("fornecedores", fornecedores.findAll());
		mv.addObject("itensMateriaisInformatica", itensMateriaisInformatica.findAll());
		
		PageWrapper<MaterialInformatica> paginaWrapper = new PageWrapper<>(materiaisInformatica.filtrar(materialInformaticaFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		MaterialInformatica materialInformatica = materiaisInformatica.findOne(codigo);
		ModelAndView mv = novo(materialInformatica);
		mv.addObject(materialInformatica);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") MaterialInformatica materialInformatica){
		try {
			cadastroMaterialInformaticaService.excluir(materialInformatica);
		}catch (ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

  }

