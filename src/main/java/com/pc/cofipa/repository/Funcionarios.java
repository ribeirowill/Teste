package com.pc.cofipa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.cofipa.model.Funcionario;
import com.pc.cofipa.repository.helper.funcionario.FuncionariosQueries;

@Repository
public interface Funcionarios extends JpaRepository<Funcionario, Long>, FuncionariosQueries{

	Optional<Funcionario> findByNomeIgnoreCase(String nome);
	
	List<Funcionario> findByNomeStartingWithIgnoreCase(String nome);

//	List<Funcionario> findByNomeRgStartingWithIgnoreCase(String nome, String rg);

	



}
