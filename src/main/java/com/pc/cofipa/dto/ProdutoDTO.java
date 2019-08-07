package com.pc.cofipa.dto;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;



public class ProdutoDTO {
	
	private Long codigo;
	private String descricao;
	private BigDecimal valorUnitario;
	private String foto;
	private String urlThumbnailFoto;
	

	public ProdutoDTO(Long codigo, String descricao, BigDecimal valorUnitario, String foto) {
		
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
		this.foto = StringUtils.isEmpty(foto) ? "produto-mock.png" : foto;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}

	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
	}

}
