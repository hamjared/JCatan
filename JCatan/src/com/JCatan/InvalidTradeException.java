package com.JCatan;

public class InvalidTradeException extends Exception {
	private static final long serialVersionUID = -844868082341516169L;

	public InvalidTradeException(String errorMessage) {
		super(errorMessage);
	}

	public InvalidTradeException() {
		// TODO Auto-generated constructor stub
	}
}