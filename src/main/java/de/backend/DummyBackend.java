package de.backend;

import java.util.List;

import org.antlrfun.Expression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.abc.exceptions.ScopeException;
import de.abc.scopes.ScopeController;

public class DummyBackend implements Backend {

	private static final Logger LOG = LogManager.getLogger(DummyBackend.class);
	
	private ScopeController scopeController;

	@Override
	public void ifStatement(final List<Expression> condition, final List<Expression> statementBody) throws ScopeException {
		
		LOG.info("Condition: " + condition);
		LOG.info("StatementBody: " + statementBody);
		
		// start a new scope for the duration of the if statement
		scopeController.descend();
		
		// leave the if-statement's scope
		scopeController.ascend();
	}
	
	public void setScopeController(ScopeController scopeController) {
		this.scopeController = scopeController;
	}

}
