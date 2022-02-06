package org.antlrfun;

import org.antlr.v4.runtime.Token;

public class Argument {

	private String value;

	private Token token;

	private int type;

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(final Token token) {
		this.token = token;
	}

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Argument [value=" + value + "]";
	}

}
