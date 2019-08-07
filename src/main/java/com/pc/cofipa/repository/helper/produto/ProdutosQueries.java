package com.pc.cofipa.repository.helper.produto;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pc.cofipa.dto.ProdutoDTO;
import com.pc.cofipa.model.Produto;
import com.pc.cofipa.repository.filter.ProdutoFilter;

public interface ProdutosQueries {
	
	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable);
	

	public List<ProdutoDTO>porDescricaoOuCodigo(String descricaoOuCodigo);

}
