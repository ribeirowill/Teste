package com.pc.cofipa.service.event.materialInformatica;

import org.springframework.util.StringUtils;

import com.pc.cofipa.model.MaterialInformatica;

public class MaterialInformaticaSalvoEvent {
	
	private MaterialInformatica materialInformatica;
	
	public MaterialInformaticaSalvoEvent(MaterialInformatica materialInformatica){
		this.materialInformatica = materialInformatica;
	}
	
	public MaterialInformatica getMaterialInformatica(){
		return materialInformatica;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(materialInformatica.getFoto());
	}
	
	public boolean isNovaFoto() {
		return materialInformatica.isNovaFoto();
	}


}
