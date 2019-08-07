package com.pc.cofipa.dto;

public class FuncionarioDTO {
	
	private Long codigo;
	private String nome;
	private String rg;
	
	
	public FuncionarioDTO(Long codigo, String nome, String rg) {
		
		this.codigo = codigo;
		this.nome = nome;
		this.rg = rg;
		
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	

}
