package org.antlrfun;

import java.util.List;

public interface ASTWalker {

//	Expression getExpression();
//
//	void setExpression(Expression expression);

	List<Expression> getExpressionList();

	List<Data> getDataList();

	void setVariableName(String varName);

	List<InitDeclarator> getInitDeclarators();

}
