package com.pc.cofipa.service.event.materialInformatica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


import com.pc.cofipa.storage.FotoStorage;

@Component
public class MaterialInformaticaListener {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#evento.temFoto() and #evento.novaFoto")
	public void MaterialInformaticaSalvo(MaterialInformaticaSalvoEvent evento){
         fotoStorage.salvar(evento.getMaterialInformatica().getFoto());
		
	}

}
