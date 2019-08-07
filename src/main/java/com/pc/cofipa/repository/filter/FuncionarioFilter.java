package com.pc.cofipa.repository.filter;

public class FuncionarioFilter {
	
	private String nome;
	private String rg;
	private String rs;
	private String carreira;
	private String ativo;
	private String email;
	
	
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
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	
	public String getCarreira() {
		return carreira;
	}
	public void setCarreira(String carreira) {
		this.carreira = carreira;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
