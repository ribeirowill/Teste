package com.pc.cofipa.service;



import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.pc.cofipa.model.MaterialMobiliario;
import com.pc.cofipa.repository.MateriaisMobiliario;

import com.pc.cofipa.service.event.materialMobiliario.MaterialMobiliarioSalvoEvent;

import com.pc.cofipa.service.exception.ImpossivelExcluirEntidadeException;
import com.pc.cofipa.storage.FotoStorage;

@Service
public class CadastroMaterialMobiliarioService {

	@Autowired
	private MateriaisMobiliario  materiaisMobiliario;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(MaterialMobiliario materialMobiliario) {
		
		publisher.publishEvent(new MaterialMobiliarioSalvoEvent(materialMobiliario));
		
		materiaisMobiliario.save(materialMobiliario);
	
	}

	@Transactional
	public void excluir(MaterialMobiliario materialMobiliario){
		try {
			String foto = materialMobiliario.getFoto(); 
			materiaisMobiliario.delete(materialMobiliario);
			materiaisMobiliario.flush();
			fotoStorage.excluir(foto);
		}catch (PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o material");	
		}
	}
	

	}