package com.pc.cofipa.service;



import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.pc.cofipa.model.MaterialInformatica;

import com.pc.cofipa.repository.MateriaisInformatica;
import com.pc.cofipa.service.event.materialInformatica.MaterialInformaticaSalvoEvent;

import com.pc.cofipa.service.exception.ImpossivelExcluirEntidadeException;
import com.pc.cofipa.storage.FotoStorage;

@Service
public class CadastroMaterialInformaticaService {

	@Autowired
	private MateriaisInformatica  materiaisInformatica;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(MaterialInformatica materialInformatica) {
		
		publisher.publishEvent(new MaterialInformaticaSalvoEvent(materialInformatica));
		
		materiaisInformatica.save(materialInformatica);
	
	}

	@Transactional
	public void excluir(MaterialInformatica materialInformatica){
		try {
			String foto = materialInformatica.getFoto(); 
			materiaisInformatica.delete(materialInformatica);
			materiaisInformatica.flush();
			fotoStorage.excluir(foto);
		}catch (PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o material");	
		}
	}
	

	}