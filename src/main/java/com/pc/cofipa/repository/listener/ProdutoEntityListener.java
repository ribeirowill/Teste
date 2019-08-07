package com.pc.cofipa.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.pc.cofipa.model.Produto;
import com.pc.cofipa.storage.FotoStorage;



public class ProdutoEntityListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@PostLoad
	public void postLoad( final Produto produto){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		produto.setUrlFoto(fotoStorage.getUrl(produto.getFotoOuMock()));
		produto.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + produto.getFotoOuMock()));
	}

}