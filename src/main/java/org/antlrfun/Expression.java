package org.antlrfun;

public class Expression {

	private String lhs;

	private String operator;

	private String rhs;

	public String getLhs() {
		return lhs;
	}

	public void setLhs(final String lhs) {
		this.lhs = lhs;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(final String operator) {
		this.operator = operator;
	}

	public String getRhs() {
		return rhs;
	}

	public void setRhs(final String rhs) {
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return "Expression [lhs='" + lhs + "', operator='" + operator + "', rhs='" + rhs + "']";
	}

}
