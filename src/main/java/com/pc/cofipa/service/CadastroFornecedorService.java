package com.pc.cofipa.service;



import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.pc.cofipa.model.Fornecedor;
import com.pc.cofipa.repository.Fornecedores;
import com.pc.cofipa.service.exception.CpfCnpjFornecedorJaCadastradoException;


@Service
public class CadastroFornecedorService {
	
	
	@Autowired
	private Fornecedores fornecedores;

	
	@Transactional
	public void salvar(Fornecedor fornecedor) {
		Optional<Fornecedor> fornecedorExistente = fornecedores.findByCpfOuCnpj(fornecedor.getCpfOuCnpjSemFormatacao());
		if (fornecedorExistente.isPresent() && !fornecedorExistente.get().equals(fornecedor)) {
			throw new CpfCnpjFornecedorJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		fornecedores.save(fornecedor);
	}


	@Transactional
	public void excluir(Long codigo){
		fornecedores.delete(codigo);
	}
	
}

