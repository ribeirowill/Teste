package com.pc.cofipa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "unidade")
public class Unidade implements Serializable  {
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "a descrição não pode estar em branco")
	@NotNull(message = "A descrição é obrigatória")
	private String descricao;
	
	@OneToMany(mappedBy = "unidade")
	private List<Produto> produtos;
	
	@OneToMany(mappedBy = "unidade")
	private List<MaterialInformatica> materiaisInformatica;
	



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

	

	public List<Produto> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public List<MaterialInformatica> getMateriaisInformatica() {
		return materiaisInformatica;
	}


	public void setMateriaisInformatica(List<MaterialInformatica> materiaisInformatica) {
		this.materiaisInformatica = materiaisInformatica;
	}


	public boolean isNovo() {
		return codigo == null;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unidade other = (Unidade) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	

}
