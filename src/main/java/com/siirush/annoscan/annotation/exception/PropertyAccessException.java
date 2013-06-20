package com.siirush.annoscan.annotation.exception;

public class PropertyAccessException extends RuntimeException {
	private static final long serialVersionUID = 2163630483408591390L;
	public PropertyAccessException() {
		super();
	}
	public PropertyAccessException(String message) {
		super(message);
	}
	public PropertyAccessException(Throwable cause) {
		super(cause);
	}
	public PropertyAccessException(String message, Throwable cause) {
		super(message,cause);
	}
}
