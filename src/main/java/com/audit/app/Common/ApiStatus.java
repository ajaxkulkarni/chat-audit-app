package com.audit.app.Common;

import java.io.Serializable;

public class ApiStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private String Message;
	private int Code;
	private Object Data;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}
}
