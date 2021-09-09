package com.murilo.ordemservico.services.exceptions;

/* 
 * Tratamento de excecoes para objeto nao encontrado (object not found exception),
 * classe que extende/herda da super classe RuntimeException e implementando seus construtores
 */
public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}

}
