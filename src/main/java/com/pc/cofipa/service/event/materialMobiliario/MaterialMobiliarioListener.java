package com.pc.cofipa.service.event.materialMobiliario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


import com.pc.cofipa.storage.FotoStorage;

@Component
public class MaterialMobiliarioListener {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#evento.temFoto() and #evento.novaFoto")
	public void MaterialMobiliarioSalvo(MaterialMobiliarioSalvoEvent evento){
         fotoStorage.salvar(evento.getMaterialMobiliario().getFoto());
		
	}

}


