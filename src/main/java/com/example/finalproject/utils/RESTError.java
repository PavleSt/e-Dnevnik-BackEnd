package com.example.finalproject.utils;

import com.example.finalproject.security.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class RESTError {

	@JsonView(Views.Student.class)
	private Integer code;
	@JsonView(Views.Student.class)
	private String message;

	public RESTError(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
