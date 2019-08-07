package com.pc.cofipa.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.cofipa.model.Funcionario;
import com.pc.cofipa.repository.Funcionarios;
import com.pc.cofipa.service.exception.ImpossivelExcluirEntidadeException;
import com.pc.cofipa.service.exception.NomeFuncionarioJaCadastradoException;

@Service
public class CadastroFuncionarioService {

	@Autowired
	private Funcionarios funcionarios;
	
	@Transactional
	public Funcionario salvar(Funcionario funcionario){
		Optional<Funcionario> funcionarioOptional = funcionarios.findByNomeIgnoreCase(funcionario.getNome());
		if(funcionarioOptional.isPresent()){
			throw new NomeFuncionarioJaCadastradoException("nome do funcionario já cadastrado");
		}
		
		return funcionarios.save(funcionario);
	}

	@Transactional
	public void excluir(Funcionario funcionario){
		try {
		funcionarios.delete(funcionario);
		}catch (PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar funcionario!");	
		}
	}
	
}

