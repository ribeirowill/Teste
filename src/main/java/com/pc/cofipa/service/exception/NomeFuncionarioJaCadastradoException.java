package com.pc.cofipa.service.exception;

public class NomeFuncionarioJaCadastradoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NomeFuncionarioJaCadastradoException(String message){
		super(message);
	}

}
