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

import com.pc.cofipa.model.MaterialMobiliario;
import com.pc.cofipa.repository.Fornecedores;
import com.pc.cofipa.repository.ItensMateriaisMobiliario;
import com.pc.cofipa.repository.MateriaisMobiliario;
import com.pc.cofipa.repository.Unidades;

import com.pc.cofipa.repository.filter.MaterialMobiliarioFilter;

import com.pc.cofipa.service.CadastroMaterialMobiliarioService;
import com.pc.cofipa.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/materiaisMobiliario")
public class MateriaisMobiliarioController {

	@Autowired
	private Unidades unidades;
	
	@Autowired
	public Fornecedores fornecedores;
	
	@Autowired
	private ItensMateriaisMobiliario itensMateriaisMobiliario;
	
	@Autowired
	private CadastroMaterialMobiliarioService cadastroMaterialMobiliarioService;
	
	@Autowired
	private MateriaisMobiliario materiaisMobiliario;
	
	
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(MaterialMobiliario materialMobiliario) {
		ModelAndView mv = new ModelAndView("materiaisMobiliario/CadastroMateriaisMobiliario");
		mv.addObject("unidades", unidades.findAll());
		mv.addObject("fornecedores", fornecedores.findAll());
		mv.addObject("itensMateriaisMobiliario", itensMateriaisMobiliario.findAll());
		return mv;
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<MaterialMobiliario> pesquisaPorCodigoItemMaterialMobiliario(
		   @RequestParam(name = "itemMaterialMobiliario", defaultValue = "-1" )Long codigoItemMaterialMobiliario) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		return materiaisMobiliario.findByItemMaterialMobiliarioCodigo(codigoItemMaterialMobiliario);
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid MaterialMobiliario materialMobiliario, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			//throw new RuntimeException();
			return novo(materialMobiliario);
		}
		
		
		cadastroMaterialMobiliarioService.salvar(materialMobiliario);
		attributes.addFlashAttribute("mensagem","Material Mobiliario salvo com sucesso!");
		return new ModelAndView("redirect:/materiaisMobiliario/novo");
				
	}
	
	@GetMapping
	public ModelAndView pesquisar(MaterialMobiliarioFilter materialMobiliarioFilter, BindingResult result
			, @PageableDefault(size = 5)Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("materiaisMobiliario/PesquisaMateriaisMobiliario");
		mv.addObject("unidades", unidades.findAll());
		mv.addObject("fornecedores", fornecedores.findAll());
		mv.addObject("itensMateriaisMobiliario", itensMateriaisMobiliario.findAll());
		
		PageWrapper<MaterialMobiliario> paginaWrapper = new PageWrapper<>(materiaisMobiliario.filtrar(materialMobiliarioFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		MaterialMobiliario materialMobiliario = materiaisMobiliario.findOne(codigo);
		ModelAndView mv = novo(materialMobiliario);
		mv.addObject(materialMobiliario);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") MaterialMobiliario materialMobiliario){
		try {
			cadastroMaterialMobiliarioService.excluir(materialMobiliario);
		}catch (ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

  }