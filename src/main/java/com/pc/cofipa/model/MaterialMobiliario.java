package com.pc.cofipa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "material_mobiliario")
public class MaterialMobiliario  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_material_mobiliario")
	private Long codigo;
	
	@NotNull(message = "O fornecedor é obrigatório")
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "codigo_fornecedor")
	private Fornecedor fornecedor;
	
	@NotNull(message = "A unidade é obrigatória")
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "codigo_unidade")
	private Unidade unidade;
	
	@NotNull(message = "O Item Material  é obrigatório")
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "codigo_item_material_mobiliario")
	private ItemMaterialMobiliario itemMaterialMobiliario;
	
	@NotBlank(message = "A descrição é obrigatória")
	@Size(min = 1, max = 150, message = "O tamanho da descrição deve estar entre 1 e 150" )
	@Column(name = "descricao_material_mobiliario")
	private String descricao;
	
	@NotNull(message = "Valor total  é obrigatório")
	@DecimalMin(value = "0.01", message = "O valor do material deve ser maior que R$0,50")
	@DecimalMax(value = "9999999.99", message = "O valor da material deve ser menor que R$9.999.999,99")
	@Column(name = "vlr_compra_material_mobiliario")
	private BigDecimal valorTotal;
	
	@NotNull(message = "Valor unitário é obrigatório")
	@DecimalMin(value = "0.01", message = "O valor da material deve ser maior que R$0,50")
	@DecimalMax(value = "9999999.99", message = "O valor da material deve ser menor que R$9.999.999,99")
	@Column(name = "vlr_unitario_material_mobiliario")
	private BigDecimal valorUnitario;
	
	@NotNull(message = "A quantidade  é obrigatória")
	@Min(value = 1, message =" A quantidade do produto não pode ser Zero")
	@Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
	@Column(name = "qtde_material_mobiliario")
	private int quantidade;
	
	
	
	//@NotBlank(message = "Número nota Fiscal  é obrigatória")
	@Column(name = "numero_nota_fiscal")
	private String numeroNotaFiscal;
	
	private String foto;
	
	@Column(name = "content_type")
	private String contentType;
	
	@Transient
	private boolean novaFoto;
	
	//@NotNull(message = "A data de Emissão é obrigatória")
	@Column(name = "data_emissao")
	private LocalDate dataEmissao;
	
	//@NotNull(message = "A data de Entrada é obrigatória")
	@Column(name = "data_entrada")
	private LocalDate dataEntrada;

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

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public ItemMaterialMobiliario getItemMaterialMobiliario() {
		return itemMaterialMobiliario;
	}

	public void setItemMaterialMobiliario(ItemMaterialMobiliario itemMaterialMobiliario) {
		this.itemMaterialMobiliario = itemMaterialMobiliario;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFotoOuMock(){
		return !StringUtils.isEmpty(foto) ? foto : "produto-mock.png";
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
		
	}
	
	public boolean isNovo(){
		return codigo == null;
	}
	
	public boolean isNovaFoto() {
		return novaFoto;
	}

	public void setNovaFoto(boolean novaFoto) {
		this.novaFoto = novaFoto;
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
		MaterialMobiliario other = (MaterialMobiliario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
