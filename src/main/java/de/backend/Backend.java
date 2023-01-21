package de.backend;

import java.util.List;

import org.antlrfun.Expression;

import de.abc.exceptions.ScopeException;

/**
 * This class is informed about language constructs that the walkers have
 * identified inside the AST. It is called backend, because I think that code
 * generators in compilers are part of the backend.
 */
public interface Backend {

	void ifStatement(List<Expression> condition, List<Expression> statementBody) throws ScopeException;

}
