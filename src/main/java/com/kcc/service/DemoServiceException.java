package com.kcc.service;

public class DemoServiceException extends RuntimeException {

	private static final long serialVersionUID = -3591074946964362142L;

	public DemoServiceException(String message) {
		super(getMessage(message));
	}

	private static String getMessage(String message) {
		StringBuilder messageSb = new StringBuilder();
		messageSb.append("receive error message [").append(message).append("]");
		return messageSb.toString();
	}

}
