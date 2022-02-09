package org.antlrfun;

import java.util.ArrayList;
import java.util.List;

public class InitDeclarator {

	private String storageClassSpecifier;

	private String name;

	private String value;

	private List<String> valueList = new ArrayList<>();

	private Expression expression;

	private final List<Expression> expressionList = new ArrayList<>();

	public String getStorageClassSpecifier() {
		return storageClassSpecifier;
	}

	public void setStorageClassSpecifier(final String storageClassSpecifier) {
		this.storageClassSpecifier = storageClassSpecifier;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public List<String> getValueList() {
		return valueList;
	}

	public void setValueList(final List<String> valueList) {
		this.valueList = valueList;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(final Expression expression) {
		this.expression = expression;
	}

	public List<Expression> getExpressionList() {
		return expressionList;
	}

	@Override
	public String toString() {
		return "InitDeclarator [storageClassSpecifier=" + storageClassSpecifier + ", name=" + name + ", value=" + value
				+ ", valueList=" + valueList + ", expression=" + expression + ", expressionList=" + expressionList
				+ "]";
	}

}
