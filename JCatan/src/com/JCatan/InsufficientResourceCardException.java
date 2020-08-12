package com.JCatan;

/**
 * @author Jared
 *
 */
public class InsufficientResourceCardException extends Exception {

	private static final long serialVersionUID = -8448680843430085319L;

	public InsufficientResourceCardException(String errorMessage) {
		System.out.println(errorMessage);
	}

	public InsufficientResourceCardException() {
		// TODO Auto-generated constructor stub
	}
}