package com.pc.cofipa.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;


import com.pc.cofipa.model.Fornecedor;
import com.pc.cofipa.repository.helper.fornecedor.FornecedoresQueries;


public interface Fornecedores extends JpaRepository<Fornecedor, Long>, FornecedoresQueries {
	
	public Optional<Fornecedor> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Fornecedor> findByNomeStartingWithIgnoreCase(String nome);



}
