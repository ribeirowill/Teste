package com.pc.cofipa.service.event.materialMobiliario;

import org.springframework.util.StringUtils;

import com.pc.cofipa.model.MaterialMobiliario;

public class MaterialMobiliarioSalvoEvent  {
	
private MaterialMobiliario materialMobiliario;
	
	public MaterialMobiliarioSalvoEvent(MaterialMobiliario materialMobiliario){
		this.materialMobiliario = materialMobiliario;
	}
	
	public MaterialMobiliario getMaterialMobiliario(){
		return materialMobiliario;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(materialMobiliario.getFoto());
	}
	
	public boolean isNovaFoto() {
		return materialMobiliario.isNovaFoto();
	}


}


