package com.pc.cofipa.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.pc.cofipa.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("local")
@Component
public class FotoStorageLocal implements FotoStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	private static final String THUMBNAIL_PREFIX = "thumbnail.";
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal(){
		this(getDefault().getPath(System.getenv("USERPROFILE"), ".cofipafotos"));
			
	}
	
	public FotoStorageLocal(Path path){
		this.local = path;
		criarPastas();
	}
	
	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if(files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException e) {	
				e.printStackTrace();
				throw new RuntimeException("Erro salvando a foto na pasta temporaria", e);
		
		    }           
		}
		
		return novoNome;
	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
            throw new RuntimeException("Erro lendo a foto temporária", e);
		}
	}
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
		   throw new RuntimeException("Erro movendo a foto para destino final" , e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
		    throw new RuntimeException("Erro gerando thumbnail", e);
		}
	}
    
	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
            throw new RuntimeException("Erro lendo a foto", e);
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String fotoProduto) {
		return recuperar(THUMBNAIL_PREFIX + fotoProduto);
	}
	
	@Override
	public byte[] recuperar2Thumbnail(String fotoPatrimonioInformatica) {
		return recuperar(THUMBNAIL_PREFIX + fotoPatrimonioInformatica);
	}
	
	@Override
	public byte[] recuperar3Thumbnail(String fotoPatrimonioMobiliario) {
		return recuperar(THUMBNAIL_PREFIX + fotoPatrimonioMobiliario);
	}
	
	@Override
	public byte[] recuperar4Thumbnail(String fotoMaterialInformatica) {
		return recuperar(THUMBNAIL_PREFIX + fotoMaterialInformatica);
	}
	
	@Override
	public byte[] recuperar5Thumbnail(String fotoMaterialMobiliario) {
		return recuperar(THUMBNAIL_PREFIX + fotoMaterialMobiliario);
	}
	
	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + foto));
		} catch (IOException e) {
			logger.warn(String.format("Erro apagando foto '%s'. Mensagem: %s", foto, e.getMessage()));
		}
		
	}
	
	@Override
	public String getUrl(String foto) {
		return "http://localhost:8080/cofipa/fotos/" + foto;
	}



	
	private void criarPastas(){
		try{
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if(logger.isDebugEnabled()){
				logger.debug("Pastas criadas para salvar foto");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				logger.debug("Pasta temporaria: " + this.localTemporario.toAbsolutePath());
				
			}
		}catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e); 
		}
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if(logger.isDebugEnabled()){
			logger.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
		}
		
		return novoNome;
	}


	
}
	


