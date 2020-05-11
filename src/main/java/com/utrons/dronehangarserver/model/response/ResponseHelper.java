package com.utrons.dronehangarserver.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseHelper<T> implements Serializable {
	private static final long serialVersionUID = 3321576411473578184L;
	private static final String CODE_SUCCESS = "0";
	private String code;
	private String message;
	private T data;

	private ResponseHelper() {
	}

	public static <T> ResponseHelper<T> newSuccess() {
		ResponseHelper response = new ResponseHelper();
		response.code = CODE_SUCCESS;
		return response;
	}

	public static <T> ResponseHelper<T> newSuccess(T data) {
		ResponseHelper response = new ResponseHelper();
		response.code = CODE_SUCCESS;
		response.data = data;
		return response;
	}

	public static <T> ResponseHelper<T> newError(String code, String message) {
		ResponseHelper response = new ResponseHelper();
		response.code = code;
		response.message = message;
		return response;
	}
}
