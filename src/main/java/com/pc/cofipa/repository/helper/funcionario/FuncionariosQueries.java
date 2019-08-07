package com.pc.cofipa.repository.helper.funcionario;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pc.cofipa.dto.FuncionarioDTO;
import com.pc.cofipa.model.Funcionario;
import com.pc.cofipa.repository.filter.FuncionarioFilter;

public interface FuncionariosQueries {
	
	public Page<Funcionario> filtrar(FuncionarioFilter filtro, Pageable pageable);
	
	public List<FuncionarioDTO> porNomeOuRg(String nomeOuRg);

}
