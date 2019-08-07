package com.pc.cofipa.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public final String THUMBNAIL_PREFIX = "thumbnail.";

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperarFotoTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperar(String foto);
	
	public byte[] recuperarThumbnail(String fotoProduto);

	public void excluir(String foto);
	
	public String getUrl(String foto);

	public byte[] recuperar2Thumbnail(String fotoPatrimonioInformatica);

	public byte[] recuperar3Thumbnail(String fotoPatrimonioMobiliario);

	public byte[] recuperar4Thumbnail(String fotoMaterialInformatica);

	public byte[] recuperar5Thumbnail(String fotoMaterialMobiliario);
	
}
